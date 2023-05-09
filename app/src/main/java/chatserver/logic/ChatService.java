package chatserver.logic;

import chatserver.gen.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    @Autowired
    private GetRoomList getRoomList;

    @Autowired
    private GetNewMessageStream getNewMessageStream;

    @Autowired
    private EnterRoom enterRoom;

    @Autowired
    private SpeechRecognize speechRecognize;

    @Autowired
    private TextToSound textToSound;

    @Autowired
    private Chat chat;

    @Autowired
    private Signup signup;

    @Override
    public void signup(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        signup.run(request, responseObserver);
    }

    @Override
    public void getRoomList(Hello request, StreamObserver<RoomInfo> responseObserver) {
        getRoomList.run(request, responseObserver);
    }

    @Override
    public void getNewMessageStream(Hello request, StreamObserver<Message> responseObserver) {
        getNewMessageStream.run(request, responseObserver);
    }

    @Override
    public void enterRoom(EnterRoomRequest request, StreamObserver<Message> responseObserver) {
        enterRoom.run(request, responseObserver);
    }

    @Override
    public StreamObserver<AudioStream> speechRecognize(StreamObserver<TextStream> responseObserver) {
        return speechRecognize.run(responseObserver);
    }

    @Override
    public void chat(ChatRequest request, StreamObserver<ChatResponseStream> responseObserver) {
        chat.run(request, responseObserver);
    }
}
