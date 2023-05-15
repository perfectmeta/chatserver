package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import chatserver.third.asr.XFYasr;
import chatserver.util.Digest;
import com.google.common.base.Strings;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

@Component
public class SpeechRecognize {
    private final Logger logger = Logger.getLogger(SpeechRecognize.class.getName());
    private final String resourcePath = !Strings.isNullOrEmpty(System.getenv("static_dir")) ?
            System.getenv("static_dir") : "./static";

    private String saveAudioContent(byte[] content) {
        var fileName = "";
        try {
            fileName = content.length + "_" + Digest.calculateMD5(content) + ".pcm";
            var file = Files.createFile(Path.of(resourcePath, fileName));
            try (var fout = new FileOutputStream(file.toFile())) {
                fout.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public StreamObserver<AudioStream> run(StreamObserver<TextStream> responseObserver) {
        PipedOutputStream out;
        InputStream in;
        try {
            var inputStream = new PipedInputStream();
            out = new PipedOutputStream(inputStream);
            in = XFYasr.makeSession(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread.startVirtualThread(()->{
            try {
                var bytes = new byte[1024];
                int len;
                while ((len = in.read(bytes)) != -1) {
                    var text = new String(bytes, 0, len, StandardCharsets.UTF_8);
                    logger.info("Get ASR Res " + text);
                    responseObserver.onNext(TextStream.newBuilder().setText(text).build());
                }
                responseObserver.onCompleted();
                logger.info("Sent response: finished");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return new StreamObserver<>() {
            private final ByteBuffer audioContent = ByteBuffer.allocate(1024*1024);

            @Override
            public void onNext(AudioStream value) {
                var audioData = value.getAudio().toByteArray();
                // logger.info("received audio data: " + audioData.length);
                try {
                    out.write(audioData);
                    audioContent.put(audioData);
                } catch (IOException e) {
                    logger.warning("OnNext exception: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                // audioContent.flip();
                // saveAudioContent(audioContent.array());
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
                logger.info("Recieved audio data finished" );
            }
        };
    }
}
