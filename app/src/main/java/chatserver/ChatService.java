package chatserver;

import chatserver.gen.*;
import io.grpc.stub.StreamObserver;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    @Override
    public void enterRoom(RoomId request, StreamObserver<RoomInfo> responseObserver) {
        RoomInfo ri = RoomInfo.newBuilder().setRoomId("test room id").build();
        responseObserver.onNext(ri);
        responseObserver.onCompleted();
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
