package chatserver.logic;

import chatserver.gen.Hello;
import chatserver.gen.Message;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class GetNewMessageStream {


    public void run(Hello request, StreamObserver<Message> responseObserver) {


    }
}
