package chatserver.logic;


import chatserver.gen.Contact;
import chatserver.gen.Hello;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetSelfInfo {
    public void run(Hello request, StreamObserver<Contact> responseObserver) {
        Objects.requireNonNull(request);

    }
}
