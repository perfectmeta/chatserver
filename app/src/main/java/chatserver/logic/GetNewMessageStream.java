package chatserver.logic;

import chatserver.gen.Hello;
import chatserver.gen.Message;
import chatserver.service.RoomService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Component
public class GetNewMessageStream {
    private static final Logger logger = Logger.getLogger(GetNewMessageStream.class.getName());
    private final RoomService roomService;

    public GetNewMessageStream(RoomService roomService) {
        this.roomService = roomService;
    }

    public void run(Hello request, StreamObserver<Message> responseObserver) {
        // todo 这个接口用于获取玩家离线后别人发来的消息，但是我们现在还没有这个玩家主动收信息的功能
        Objects.requireNonNull(request);
        var user = AuthTokenInterceptor.USER.get();
        var userBlackboard = AuthTokenInterceptor.BLACKBOARD.get();
        userBlackboard.registerMessageStreamObserver(responseObserver);

        // 先把所有房间的最新x条消息再推一遍吧
        List<chatserver.entity.Message> messageList = roomService.getNewestMessageEachRoom(user.getUserId());
        for (var message : messageList) {
            logger.info("Get Message " + message.getText());
            var rpcMessage = Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setAudioUrl(message.getAudioUrl() == null ? "" : message.getAudioUrl())
                .setText(message.getText() == null ? "" : message.getText())
                .setCreatedTime(message.getCreatedTime())
                .setImageUrl(message.getImageUrl() == null ? "" : message.getImageUrl())
                .setMsgType(message.getMsgType().toProto())
                .build();
            responseObserver.onNext(rpcMessage);
        }
    }
}
