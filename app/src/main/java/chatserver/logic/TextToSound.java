package chatserver.logic;

import chatserver.gen.AudioStream;
import chatserver.gen.TextStream;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class TextToSound {
    public StreamObserver<TextStream> run(StreamObserver<AudioStream> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(TextStream value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }
}
