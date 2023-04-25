package chatserver.logic;

import chatserver.controller.UserController;
import chatserver.gen.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    @Autowired
    private UserController users;

    @Override
    public void listRoom(Hello request, StreamObserver<RoomInfo> responseObserver) {
        users.findByUserId(1234);
        RoomInfo ri = RoomInfo.newBuilder().setRoomId("test room id").build();
        responseObserver.onNext(ri);
        responseObserver.onCompleted();
    }

    @Override
    public void getMessageStream(Hello request, StreamObserver<Message> responseObserver) {
        super.getMessageStream(request, responseObserver);
    }

    @Override
    public StreamObserver<AudioStream> speechRecognize(StreamObserver<TextStream> responseObserver) {
        return super.speechRecognize(responseObserver);
    }

    @Override
    public void chat(TextMessage request, StreamObserver<TextAudioStream> responseObserver) {
        super.chat(request, responseObserver);
    }
}
