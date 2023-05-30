package chatserver.logic;

import chatserver.gen.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final GetSelfInfo getSelfInfo;
    private final Signup signup;
    private final GetRoomStream getRoomList;
    private final GetContactStream getContactList;
    private final GetNewMessageStream getNewMessageStream;
    private final EnterRoom enterRoom;
    private final SpeechRecognize speechRecognize;
    private final Chat chat;
    private final GetMemory getMemory;
    private final DeleteMemory deleteMemory;

    private final CreateMineNPCs createMineNPCs;
    private final EstablishContactWith establishContactWith;


    @Autowired
    public ChatService(GetSelfInfo getSelfInfo, GetRoomStream getRoomList,
                       GetContactStream getContactList,
                       GetNewMessageStream getNewMessageStream,
                       EnterRoom enterRoom,
                       SpeechRecognize speechRecognize,
                       Chat chat,
                       Signup signup,
                       GetMemory getMemory,
                       DeleteMemory deleteMemory,
                       CreateMineNPCs createMineNPCs,
                       EstablishContactWith establishContactWith) {
        this.getSelfInfo = getSelfInfo;
        this.getRoomList = getRoomList;
        this.getContactList = getContactList;
        this.getNewMessageStream = getNewMessageStream;
        this.enterRoom = enterRoom;
        this.speechRecognize = speechRecognize;
        this.chat = chat;
        this.signup = signup;
        this.getMemory = getMemory;
        this.deleteMemory = deleteMemory;
        this.createMineNPCs = createMineNPCs;
        this.establishContactWith = establishContactWith;
    }

    @Override
    public void signup(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        signup.run(request, responseObserver);
    }

    @Override
    public void getSelfInfo(Hello request, StreamObserver<Contact> responseObserver) {
        getSelfInfo.run(request, responseObserver);
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

    @Override
    public void getMemory(GetMemoryRequest request, StreamObserver<Memory> responseObserver) {
        getMemory.run(request, responseObserver);
    }

    @Override
    public void deleteMemory(DeleteMemoryRequest request, StreamObserver<DeleteMemoryResponse> responseStreamObserver) {
        deleteMemory.run(request, responseStreamObserver);
    }

    @Override
    public void createMineNPCs(CreateMineNPCsRequest request, StreamObserver<CreateMineNPCsResponse> responseObs) {
        createMineNPCs.run(request, responseObs);
    }

    @Override
    public void establishContactWith(EstablishContactWithRequest request,
                                     StreamObserver<EstablishContactWithResponse> responseObs) {
        establishContactWith.run(request, responseObs);
    }
}
