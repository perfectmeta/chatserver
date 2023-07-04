package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import chatserver.third.asr.XFYasr;
import chatserver.util.Digest;
import chatserver.util.StopSignal;
import com.google.common.base.Strings;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class SpeechRecognize {
    private final Logger logger = Logger.getLogger(SpeechRecognize.class.getName());
    private final static String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : "./static";

    static class AudioBuffer {
        private static final Logger logger = Logger.getLogger(AudioBuffer.class.getName());
        private final ByteBuffer bf;
        public AudioBuffer() {
            bf = ByteBuffer.allocate(1024*1024*8);
        }

        public void write(byte[] bytes) {
            bf.put(bytes);
        }

        private String save() {
            bf.flip();
            var fileName = bf.limit() + "_" + Digest.calculateMD5(bf.array()) + ".pcm";
            try {
                var file = Files.createFile(Path.of(resourcePath, fileName));
                try (var fout = new FileOutputStream(file.toFile())) {
                    fout.write(bf.array(), 0, bf.limit());
                }
            } catch (IOException e) {
                if (!(e instanceof FileNotFoundException)) {
                    // e.printStackTrace();
                    logger.warning("save audio " + e.getMessage());
                }
            }
            return fileName;
        }
    }

    public StreamObserver<AudioStream> run(StreamObserver<TextStream> responseObserver) {
        PipedOutputStream out;
        BlockingQueue<Object> blockingQueue;
        AudioBuffer audioBuffer = new AudioBuffer();
        try {
            var inputStream = new PipedInputStream();
            out = new PipedOutputStream(inputStream);
            blockingQueue = XFYasr.makeSession(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread.startVirtualThread(() -> {
            try {
                while (true) {
                    var o = blockingQueue.poll(15, TimeUnit.SECONDS);
                    if (o == null || o instanceof StopSignal) {
                        if (o != null) {
                            logger.warning("StopSignal received");
                        }
                        break;
                    }
                    TextStream textStream = (TextStream) o;
                    logger.info("Get ASR Res " + textStream.getText() + " del " + textStream.getDelete());
                    responseObserver.onNext(textStream);
                }
                var filePath = audioBuffer.save();
                responseObserver.onNext(TextStream.newBuilder().setAudioUrl(filePath).build());
                logger.info("Sent response: finished url: " + filePath);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                responseObserver.onCompleted();
            }
        });
        return new StreamObserver<>() {
            @Override
            public void onNext(AudioStream value) {
                var audioData = value.getAudio().toByteArray();
                // logger.info("received audio data: " + audioData.length);
                try {
                    out.write(audioData);
                    audioBuffer.write(audioData);
                } catch (IOException e) {
                    logger.warning("OnNext exception: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                try {
                    out.close();
                    logger.warning("OnError exception: " + t.getMessage());
                    t.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.warning(t.getMessage());
                }
            }

            @Override
            public void onCompleted() {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.warning(e.getMessage());
                }
                logger.info("Recieved audio data finished");
            }
        };
    }
}
