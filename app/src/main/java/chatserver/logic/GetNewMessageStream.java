package chatserver.logic;

import chatserver.gen.Hello;
import chatserver.gen.Message;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class GetNewMessageStream {

    public void run(Hello request, StreamObserver<Message> responseObserver) {
    }
}
