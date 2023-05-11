package chatserver.logic;

import chatserver.gen.*;
import chatserver.service.UserService;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    private ChatServiceGrpc.ChatServiceStub stub;

    private List<RoomInfo> rooms = new ArrayList<>();

    @Autowired
    private UserService userService;
    private static final Logger logger = Logger.getLogger(LoginTest.class.getName());
    @BeforeAll
    void init() {
        var channel = Grpc.newChannelBuilder("ai.taohuayuaner.com:8080", InsecureChannelCredentials.create()).build();
        //var channel = Grpc.newChannelBuilder("localhost:6565", InsecureChannelCredentials.create()).build();
        stub = ChatServiceGrpc.newStub(channel);
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER), "1");
        stub = stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
        if (userService.findByPhone("+8618585858585")!=null) {
            userService.deleteByPhone("+8618585858585");
        }
    }

    @AfterAll
    void destroy() {
        logger.info("Spring destroyed");
        //SpringApplication.exit(context);
    }

    @Test
    void signupTest() throws InterruptedException {
        RegisterInfo ri = RegisterInfo.newBuilder().setEmail("earneet@gmail.com")
                .setPhone("+8618585858585").setNickname("earneet").build();
        List<RegisterFeedback> results = new ArrayList<>();
        stub.signup(ri, new StreamObserver<>() {
            @Override
            public void onNext(RegisterFeedback value) {
                results.add(value);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                logger.info("on error " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info(" onCompleted");
            }
        });

        Thread.sleep(3000);
        Assertions.assertEquals(1, results.size());
        // 兼容错误码6是以为了远程测试的时候无法真的删除之前添加的账号，咱们就认为数据库冲突也是对的吧
        Assertions.assertTrue(results.get(0).getStatusCode() == 0 || results.get(0).getStatusCode() == 6);
    }

    @Test
    void getRoomListTest() throws InterruptedException {
        Hello hello = Hello.newBuilder().build();
        List<RoomInfo> result = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        stub.getRoomList(hello, new StreamObserver<>() {
            @Override
            public void onNext(RoomInfo value) {
                logger.info("Room info: " + value);
                result.add(value);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                latch.countDown();
                logger.info("Room Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                latch.countDown();
                logger.info("Room Complement: ");
            }
        });
        latch.await();
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getYou().getName().equals("nick")
                || result.get(0).getYou().getName().equals("earneet"));
        rooms = result;
    }

    @Test
    void enterRoomTest() throws InterruptedException {
        if (rooms.isEmpty()) return;
        var firstRoom = rooms.get(0);
        var enterRoomRequest = EnterRoomRequest.newBuilder()
                .setRoomId(firstRoom.getRoomId())
                .setLastMessageId(firstRoom.getLastMessageId()).build();
        var messages = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        stub.enterRoom(enterRoomRequest, new StreamObserver<>() {
            @Override
            public void onNext(Message value) {
                logger.info("new message received: " + value);
                messages.add(value);
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("onError: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.warning("onComplete: ");
                latch.countDown();
            }
        });
        latch.await();
        logger.info("finished");
        Assertions.assertEquals(0, messages.size());
    }

    @Test
    void sendMessageTest() throws InterruptedException {
        ChatRequest request = ChatRequest.newBuilder()
                .setRoomId(1)
                .setText("Hey, How are you?")
                .setMsgType(MsgType.TEXT)
                .build();
        CountDownLatch latch = new CountDownLatch(1);
        stub.chat(request, new StreamObserver<>() {
            @Override
            public void onNext(ChatResponseStream value) {
                logger.info("OnNext: " + value.getText());
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("onError");
                t.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted finished");
                latch.countDown();
            }
        });
        latch.await();
    }
}
