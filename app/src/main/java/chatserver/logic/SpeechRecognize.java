package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import chatserver.third.asr.XFYasr;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class SpeechRecognize {

    private String saveAudioContent(byte[] content) {
        // todo to save AudioContent
        return "";
    }

    public StreamObserver<AudioStream> run(StreamObserver<TextStream> responseObserver) {
        PipedOutputStream out;
        InputStream in;
        try {
            var inputStream = new PipedInputStream();
            out = new PipedOutputStream(inputStream);
            in = XFYasr.listen(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new StreamObserver<>() {
            private final ByteBuffer audioContent = ByteBuffer.allocate(1024);
            private boolean finished = false;

            @Override
            public void onNext(AudioStream value) {
                var audioData = value.getAudio().toByteArray();
                try {
                    out.write(audioData);
                    audioContent.put(audioData);
                    var bytes = new byte[1024];
                    var len = in.read(bytes);
                    if (len == -1 && !finished) {
                        responseObserver.onNext(TextStream.newBuilder()
                                .setAudioUrl(saveAudioContent(audioContent.array())).build());
                        responseObserver.onCompleted();
                        finished = true;
                        return;
                    }
                    var text = new String(bytes, 0, len, StandardCharsets.UTF_8);
                    responseObserver.onNext(TextStream.newBuilder().setText(text).build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private void waitAllResponse() throws IOException {
                if (finished) return;
                var bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    var text = new String(bytes, 0, len, StandardCharsets.UTF_8);
                    responseObserver.onNext(TextStream.newBuilder().setText(text).build());
                }
                responseObserver.onCompleted();
                finished = true;
            }

            @Override
            public void onError(Throwable t) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCompleted() {
                try {
                    out.flush();
                    out.close();
                    waitAllResponse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
