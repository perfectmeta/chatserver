package chatserver.logic.voice;

import chatserver.util.Digest;
import com.google.common.base.Strings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class VoiceTransfer {
    private static final Logger logger = Logger.getLogger(VoiceTransfer.class.getName());

    enum Status {
        READY,
        START,
        FINISHED
    }

    private static final char[] _spliters = {',', '。', '.', '，', '?', '？', '!', '！'};
    private static final Set<Character> spliters = new HashSet<>();
    static {
        for (var s : _spliters) {
            spliters.add(s);
        }
    }
    private final String resourcePath;
    private final BiConsumer<byte[], Boolean> callBack;
    private final Queue<String> taskQueue;
    private final List<String> rawTexts;
    private StringBuilder tempFragment;
    private Status status;
    private Consumer<String> finishCallback;
    private final Speaker[] speakers;

    public VoiceTransfer(BiConsumer<byte[], Boolean> callBack, String resourcePath, Speaker... speaker) {
        this.callBack = callBack;
        this.taskQueue = new LinkedBlockingDeque<>();
        this.rawTexts = new ArrayList<>();
        this.status = Status.READY;
        this.tempFragment = new StringBuilder();
        this.resourcePath = resourcePath;
        this.speakers = speaker;
    }

    public void update(String newMessage) {
        int offset = 0;
        rawTexts.add(newMessage);
        for (int i = 0; i < newMessage.length(); i++) {
            if (spliters.contains(newMessage.charAt(i))) {
                offset = i + 1;
            }
        }

        if (offset > 0) {
            tempFragment.append(newMessage, 0, offset);
            String fragment = tempFragment.toString();
            var content = clipFragment(fragment);
            if (!Strings.isNullOrEmpty(content)) {
                addTask(content);
                logger.info("Add Task: " + fragment);
            }
            tempFragment = new StringBuilder(newMessage.substring(offset));
        } else {
            tempFragment.append(newMessage);
        }

        if (status == Status.READY) {
            startWork();
        }
    }

    // 裁剪掉英文字母开头的部分和标点符号开头的部分
    public static String clipFragment(String fragment) {
        if (Strings.isNullOrEmpty(fragment)) {
            return fragment;
        }
        fragment = fragment.trim();
        for (int i = 0, end = fragment.length(); i < end; i++) {
            var currentCharacter = fragment.charAt(i);
            if (!isAlphabetic(currentCharacter) && !isPunctuation(currentCharacter)) {
                if (i == 0) {
                    return fragment;
                }

                return fragment.substring(i);
            }
        }
        return "";
    }

    private static boolean isAlphabetic(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    private static boolean isPunctuation(char ch) {
        int type = Character.getType(ch);
        return type == Character.OTHER_PUNCTUATION || type == Character.CONNECTOR_PUNCTUATION
                || type == Character.DASH_PUNCTUATION || type == Character.END_PUNCTUATION
                || type == Character.FINAL_QUOTE_PUNCTUATION || type == Character.INITIAL_QUOTE_PUNCTUATION
                || type == Character.START_PUNCTUATION;
    }

    private void startWork() {
        status = Status.START;
        Thread.startVirtualThread(() -> {
            while (status != Status.FINISHED || !taskQueue.isEmpty()) {
                var content = taskQueue.poll();
                if (Strings.isNullOrEmpty(content)) {
                    continue;
                }

                var finished = status == Status.FINISHED && taskQueue.isEmpty();
                byte[] audio = getAudio(content);
                if (audio != null) {
                    callBack.accept(audio, finished);
                }
                logger.info("finishe ars task for content: " + content);
                if (finished) {
                    break;
                }
            }

            var fullContent = String.join("", rawTexts);
            byte[] audio = getAudio(fullContent);
            var fileName = "failed";
            if (audio != null) {
                logger.info("saving TTS");
                fileName = saveTTS(audio);
            } else {
                logger.warning("saving TTS failed since full content audio generate failed");
            }
            if (this.finishCallback != null) {
                this.finishCallback.accept(fileName);
            }
        });
    }

    private byte[] getAudio(String content) {
        byte[] audio = null;
        for (var speaker : speakers) {
            try {
                audio = speaker.getAudio(content);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.warning("speaker %s failed".formatted(speaker.getClass().getName()));
            }
            if (audio != null)
                break;
        }
        return audio;
    }

    private void addTask(String content) {
        taskQueue.add(content);
    }

    public void finish(Consumer<String> finishCallback) {
        String remainFragment = clipFragment(tempFragment.toString());
        if (!Strings.isNullOrEmpty(remainFragment)) {
            addTask(remainFragment);
            logger.info("Add Task: " + tempFragment);
        }
        this.status = Status.FINISHED;
        this.finishCallback = finishCallback;
    }

    private String saveTTS(byte[] allContent) {
        var fileName = allContent.length + "_" + Digest.calculateMD5(allContent) + ".mp3";
        Path file;
        try {
            //这一步可能文件已经存在了，就不需要再写入文件，直接返回文件名即可
            file = Files.createFile(Path.of(resourcePath, fileName));
            logger.info("Save mp3 file " + file);
        } catch (IOException e) {
            logger.warning("conflict file " + fileName);
            return fileName;
        }
        try (var fout = new FileOutputStream(file.toFile())) {
            fout.write(allContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
