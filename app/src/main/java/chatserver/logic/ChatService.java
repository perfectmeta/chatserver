package chatserver.logic;

import chatserver.gen.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final GetRoomList getRoomList;
    private final GetContactList getContactList;

    private final GetNewMessageStream getNewMessageStream;

    private final EnterRoom enterRoom;

    private final SpeechRecognize speechRecognize;

    private final Chat chat;

    private final Signup signup;

    @Autowired
    public ChatService(GetRoomList getRoomList,
                       GetContactList getContactList,
                       GetNewMessageStream getNewMessageStream,
                       EnterRoom enterRoom,
                       SpeechRecognize speechRecognize,
                       Chat chat,
                       Signup signup) {
        this.getRoomList = getRoomList;
        this.getContactList = getContactList;
        this.getNewMessageStream = getNewMessageStream;
        this.enterRoom = enterRoom;
        this.speechRecognize = speechRecognize;
        this.chat = chat;
        this.signup = signup;
    }

    @Override
    public void signup(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        signup.run(request, responseObserver);
    }

    @Override
    public void getRoomStream(Hello request, StreamObserver<RoomInfo> responseObserver) {
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

    @Override
    public void getContactStream(Hello request, StreamObserver<Contact> responseObserver) {
        getContactList.run(request, responseObserver);
    }
}
