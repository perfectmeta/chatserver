package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import chatserver.third.asr.XFYasr;
import chatserver.util.Digest;
import chatserver.util.StopSignal;
import com.google.common.base.Strings;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

@Component
public class SpeechRecognize {
    private final Logger logger = Logger.getLogger(SpeechRecognize.class.getName());
    private final static String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : "./static";

    static class AudioBuffer {
        private final ByteBuffer bf;
        public AudioBuffer() {
            bf = ByteBuffer.allocate(1024*1024*8);
        }

        public void write(byte[] bytes) {
            bf.put(bytes);
        }

        private String save() {
            bf.flip();
            var fileName = bf.remaining() + "_" + Digest.calculateMD5(bf.array()) + ".pcm";
            try {
                var file = Files.createFile(Path.of(resourcePath, fileName));
                try (var fout = new FileOutputStream(file.toFile())) {
                    fout.write(bf.array(), 0, bf.remaining());
                }
            } catch (IOException e) {
                e.printStackTrace();
                // throw new RuntimeException(e);
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
                    var o = blockingQueue.take();
                    if (o instanceof StopSignal) {
                        logger.warning("StopSignal received");
                        break;
                    }
                    TextStream textStream = (TextStream) o;
                    logger.info("Get ASR Res " + textStream.getText());
                    responseObserver.onNext(textStream);
                }
                var filePath = audioBuffer.save();
                responseObserver.onNext(TextStream.newBuilder().setAudioUrl(filePath).build());
                responseObserver.onCompleted();
                logger.info("Sent response: finished");
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return new StreamObserver<>() {
            private final ByteBuffer audioContent = ByteBuffer.allocate(1024 * 1024);

            @Override
            public void onNext(AudioStream value) {
                var audioData = value.getAudio().toByteArray();
                // logger.info("received audio data: " + audioData.length);
                try {
                    out.write(audioData);
                    audioBuffer.write(audioData);
                    audioContent.put(audioData);
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
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCompleted() {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Recieved audio data finished");
            }
        };
    }
}
