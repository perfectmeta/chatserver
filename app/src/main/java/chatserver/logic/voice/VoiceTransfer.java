package chatserver.logic.voice;

import chatserver.third.tts.Pwrdtts;
import chatserver.third.tts.XFYtts;
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

    private static final String[] _spliters = {",", "。", ".", "，", "?", "？", "!", "！"};
    private static final Set<String> spliters = new HashSet<>(Arrays.asList(_spliters));
    private final String resourcePath;
    private final BiConsumer<byte[], Boolean> callBack;
    private final Queue<String> taskQueue;
    private final List<String> rawTexts;
    private StringBuilder tempFragment;
    private Status status;
    private Consumer<String> finishCallback;
    private final boolean isUsePwrdtts;

    public VoiceTransfer(BiConsumer<byte[], Boolean> callBack, String resourcePath, boolean isUsePwrdtts) {
        this.callBack = callBack;
        this.taskQueue = new LinkedBlockingDeque<>();
        this.rawTexts = new ArrayList<>();
        this.status = Status.READY;
        this.tempFragment = new StringBuilder();
        this.resourcePath = resourcePath;
        this.isUsePwrdtts = isUsePwrdtts;
    }

    public void update(String newMessage) {
        int offset = 0;
        rawTexts.add(newMessage);
        for (int i = 0; i < newMessage.length(); i++) {
            if (spliters.contains(newMessage.substring(i, i + 1))) {
                tempFragment.append(newMessage, offset, i + 1);
                offset = i + 1;
                var fragment = tempFragment.toString();
                if (!Strings.isNullOrEmpty(fragment)) {
                    addTask(fragment);
                    logger.info("Add Task: " + fragment);
                }
                tempFragment = new StringBuilder();
            }
        }

        tempFragment.append(newMessage, offset, newMessage.length());

        if (status == Status.READY) {
            status = Status.START;
            Thread.startVirtualThread(() -> {
                while (status != Status.FINISHED || !taskQueue.isEmpty()) {
                    var content = taskQueue.poll();
                    if (Strings.isNullOrEmpty(content)) {
                        continue;
                    }

                    var finished = status == Status.FINISHED && taskQueue.isEmpty();
                    if (isUsePwrdtts) {
                        byte[] audio = Pwrdtts.tts(content);
                        if (audio != null) {
                            callBack.accept(audio, finished);
                        }

                    } else {
                        try (var audioStream = XFYtts.makeSession(content)) {
                            callBack.accept(audioStream.readAllBytes(), finished);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    logger.info("finishe ars task for content: " + content);
                    if (finished) {
                        break;
                    }
                }

                var fullContent = String.join("", rawTexts);
                byte[] audio;
                if (isUsePwrdtts) {
                    audio = Pwrdtts.tts(fullContent);
                } else {
                    try (var audioStream = XFYtts.makeSession(fullContent)) {
                        audio = audioStream.readAllBytes();
                    } catch (IOException e) {
                        logger.warning(e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

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
    }

    private void addTask(String content) {
        taskQueue.add(content);
    }

    public void finish(Consumer<String> finishCallback) {
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
