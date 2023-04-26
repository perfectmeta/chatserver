package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class SpeechRecognize {
    public StreamObserver<AudioStream> run(StreamObserver<TextStream> responseObserver) {
        return null;
    }
}
