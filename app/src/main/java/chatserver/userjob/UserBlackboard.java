package chatserver.userjob;

import chatserver.gen.Contact;
import chatserver.gen.Message;
import chatserver.gen.RoomInfo;
import io.grpc.stub.StreamObserver;

public class UserBlackboard {
    private StreamObserver<Contact> contactStreamObserver;
    private StreamObserver<RoomInfo> roomInfoStreamObserver;
    private StreamObserver<Message> messageStreamObserver;

    public void registerContactStreamObserver(StreamObserver<Contact> streamObserver) {
        this.contactStreamObserver = streamObserver;
    }

    public void registerRoomInfoStreamObserver(StreamObserver<RoomInfo> streamObserver) {
        this.roomInfoStreamObserver = streamObserver;
    }

    public void registerMessageStreamObserver(StreamObserver<Message> streamObserver) {
        this.messageStreamObserver = streamObserver;
    }

    public void newMessageNotify(Message newMessage) {
        UserJobManager.getInstance().commitJob(new UserJobBase(){
            @Override
            public void run() {
                messageStreamObserver.onNext(newMessage);
            }
        });
    }

    public void newRoomInfoNotify(RoomInfo newRoomInfo) {
        UserJobManager.getInstance().commitJob(new UserJobBase() {
            @Override
            public void run() {
                roomInfoStreamObserver.onNext(newRoomInfo);
            }
        });
    }

    public void newContactNotify(Contact newContact) {
        UserJobManager.getInstance().commitJob(new UserJobBase() {
            @Override
            public void run() {
                contactStreamObserver.onNext(newContact);
            }
        });
    }
}
