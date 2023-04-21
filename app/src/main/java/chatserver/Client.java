package chatserver;

import chatserver.gen.ChatServiceGrpc;
import chatserver.gen.RoomId;
import chatserver.gen.RoomInfo;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Client {
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    private final ChatServiceGrpc.ChatServiceStub asyncStub;

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8980";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        Client client = new Client(channel);

        RoomInfo ri = client.enterRoom();
        System.out.print("blocking enter room ok: " + ri.toString());

        CountDownLatch finishLatch = new CountDownLatch(1);
        client.asyncEnterRoom(finishLatch);
        if (!finishLatch.await(1, TimeUnit.SECONDS)){
            System.out.println("exit! do not wait");
        }
    }

    public Client(Channel channel) {
        blockingStub = ChatServiceGrpc.newBlockingStub(channel);
        asyncStub = ChatServiceGrpc.newStub(channel);
    }

    public RoomInfo enterRoom() {
        return blockingStub.enterRoom(RoomId.newBuilder().setRoomId("my room id").build());
    }

    public void asyncEnterRoom(CountDownLatch finishLatch) {
        StreamObserver<RoomInfo> observer = new StreamObserver<>() {

            @Override
            public void onNext(RoomInfo value) {
                System.out.print("async enter room ok: " + value);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("async enter room completed");
                finishLatch.countDown();
            }
        };
        asyncStub.enterRoom(RoomId.newBuilder().setRoomId("my room id").build(), observer);
    }
}
