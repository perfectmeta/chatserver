package chatserver.logic;

import chatserver.gen.Hello;
import chatserver.gen.Message;
import chatserver.userjob.UserJobManager;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Queue;

@Component
public class GetNewMessageStream {
    public void run(Hello request, StreamObserver<Message> responseObserver) {
        // todo 这个接口用于获取玩家离线后别人发来的消息，但是我们现在还没有这个玩家主动收信息的功能
        Objects.requireNonNull(request);
        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerMessageStreamObserver(responseObserver);
    }
}
