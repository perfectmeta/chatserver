package chatserver.logic.voice;

import chatserver.third.tts.Pwrdtts;
import chatserver.third.tts.XFYtts;
import chatserver.util.Digest;
import com.google.common.base.Strings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
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
    private final Queue<String> queue;
    private final ByteBuffer byteBuffer;
    private StringBuilder tempFragment;
    private Status status;
    private Consumer<String> finishCallback;
    private boolean isUsePwrdtts;

    public VoiceTransfer(BiConsumer<byte[], Boolean> callBack, String resourcePath, boolean isUsePwrdtts) {
        this.callBack = callBack;
        this.queue = new LinkedBlockingDeque<>();
        this.status = Status.READY;
        this.tempFragment = new StringBuilder();
        this.byteBuffer = ByteBuffer.allocate(1024 * 1024);
        this.resourcePath = resourcePath;
        this.isUsePwrdtts = isUsePwrdtts;
    }

    public void update(String newMessage) {
        int offset = 0;
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
                while (status != Status.FINISHED || !queue.isEmpty()) {
                    var content = queue.poll();
                    if (Strings.isNullOrEmpty(content)) {
                        continue;
                    }

                    if (isUsePwrdtts) {
                        byte[] audio = Pwrdtts.tts(content);
                        boolean finished = status == Status.FINISHED && queue.isEmpty();
                        if (audio != null) {
                            byteBuffer.put(audio);
                            callBack.accept(audio, finished);
                        }
                        if (finished) {
                            break;
                        }
                    } else {
                        try (var audioStream = XFYtts.makeSession(content)) {
                            byte[] audio = audioStream.readAllBytes();
                            byteBuffer.put(audio);
                            boolean finished = status == Status.FINISHED && queue.isEmpty();
                            callBack.accept(audio, finished);
                            if (finished) {
                                break;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                byteBuffer.flip();
                logger.info("saving TTS");
                var fileName = saveTTS(byteBuffer.array(), 0, byteBuffer.limit());
                if (this.finishCallback != null) {
                    this.finishCallback.accept(fileName);
                }
            });
        }
    }

    private void addTask(String content) {
        queue.add(content);
    }

    public void finish(Consumer<String> finishCallback) {
        this.status = Status.FINISHED;
        this.finishCallback = finishCallback;
    }

    private String saveTTS(byte[] allContent, int offset, int length) {
        var fileName = length + "_" + Digest.calculateMD5(allContent, offset, length) + ".mp3";
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
            fout.write(allContent, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
