package chatserver.logic;

import chatserver.gen.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    @Autowired
    private GetRoomList getRoomList;

    @Override
    public void getRoomList(Hello request, StreamObserver<RoomInfo> responseObserver) {
        getRoomList.run(request, responseObserver);
    }

    @Override
    public void getNewMessageStream(Hello request, StreamObserver<Message> responseObserver) {
        super.getNewMessageStream(request, responseObserver);
    }

    @Override
    public void enterRoom(EnterRoomReq request, StreamObserver<Message> responseObserver) {
        super.enterRoom(request, responseObserver);
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
