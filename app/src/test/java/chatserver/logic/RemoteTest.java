package chatserver.logic;

import chatserver.gen.*;
import com.google.protobuf.ByteString;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RemoteTest {
    private ChatServiceGrpc.ChatServiceStub stub;

    private static final Logger logger = Logger.getLogger(LoginTest.class.getName());
    @BeforeAll
    void init() {
        //var channel = Grpc.newChannelBuilder("ai.taohuayuaner.com:9080", InsecureChannelCredentials.create()).build();
        var channel = Grpc.newChannelBuilder("localhost:9080", InsecureChannelCredentials.create()).build();
        stub = ChatServiceGrpc.newStub(channel);
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER), "1");
        stub = stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    @AfterAll
    void destroy() {
        logger.info("Spring destroyed");
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
        stub.getRoomStream(hello, new StreamObserver<>() {
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
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.get(0).getYou().getName().equals("nick")
                || result.get(0).getYou().getName().equals("earneet"));
    }

    @Test
    void enterRoomTest() throws InterruptedException {
        var roomId = 1;
        var lastMessageId = 0;
        var enterRoomRequest = EnterRoomRequest.newBuilder()
                .setRoomId(roomId)
                .setLastMessageId(lastMessageId).build();
        CountDownLatch latch = new CountDownLatch(1);
        var success = new boolean[]{false};
        stub.enterRoom(enterRoomRequest, new StreamObserver<>() {
            @Override
            public void onNext(Message value) {
                logger.info("new message received: " + value);
            }

            @Override
            public void onError(Throwable t) {
                logger.warning("onError: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.warning("onComplete: ");
                success[0] = true;
                latch.countDown();
            }
        });
        latch.await();
        logger.info("finished");
        Assertions.assertTrue(success[0]);
    }

    @Test
    void sendMessageTwice() throws Exception {
        sendMessageTest();
        sendMessageTest();
    }

    @Test
    void sendMessageTest() throws InterruptedException {
        ChatRequest request = ChatRequest.newBuilder()
                .setRoomId(1)
                .setText("Hey, 你是AI吗?你有名字吗?")
                .setSeq("aabb")
                .setMsgType(MsgType.TEXT)
                .build();
        CountDownLatch latch = new CountDownLatch(1);
        stub.chat(request, new StreamObserver<>() {
            @Override
            public void onNext(ChatResponseStream value) {
                if (value.hasResponseMessage()) {
                    logger.info(value.getResponseMessage().getText());
                    logger.info(value.getResponseMessage().getAudioUrl());
                    return;
                }

                if (value.getAudio().toByteArray() != null && value.getAudio().toByteArray().length > 0) {
                    logger.warning("Audio R size " + value.getAudio().size());
                }

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

    @Test
    public void asrTest() throws IOException, InterruptedException {
        var audioPath = Paths.get("src/test/resources/iat_pcm_16k.pcm").toAbsolutePath();
        logger.info(audioPath.toString());
        ByteBuffer bytes;
        CountDownLatch latch;
        StreamObserver<AudioStream> audioInput;
        try (FileInputStream fis = new FileInputStream(audioPath.toFile())) {
            bytes = ByteBuffer.allocate(1024);
            latch = new CountDownLatch(1);
            audioInput = stub.speechRecognize(new StreamObserver<>() {
                @Override
                public void onNext(TextStream value) {
                    logger.info("Get Response Client " + value.getText());
                    bytes.put(value.getText().getBytes(StandardCharsets.UTF_8));
                    if (!Strings.isNullOrEmpty(value.getAudioUrl())) {
                        logger.info("Get URL  " + value.getAudioUrl());
                    }
                }

                @Override
                public void onError(Throwable t) {
                    logger.warning("Error" + t.getMessage());
                    t.printStackTrace();
                    latch.countDown();
                }

                @Override
                public void onCompleted() {
                    logger.warning("Complete");
                    latch.countDown();
                }
            });

            byte[] buffer = new byte[1280];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                var as = AudioStream.newBuilder().setAudio(ByteString.copyFrom(buffer, 0, len));
                audioInput.onNext(as.build());
            }
        }
        audioInput.onCompleted();

        latch.await();
        bytes.flip();
        var content = new String(bytes.array(), 0, bytes.remaining(), StandardCharsets.UTF_8);
        Assertions.assertEquals("语音听写可以将语音转为文字。", content);
    }

    @Test
    public void getContactStreamTest() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        stub.getContactStream(Hello.newBuilder().build(), new StreamObserver<>() {
            @Override
            public void onNext(Contact value) {
                logger.info("Get Contact Result " + value.toString());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                Assertions.fail("Error occurred");
                logger.warning(t.getMessage());
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                Assertions.fail();
            }
        });
        latch.await();
    }

    @Test
    public void getMemoryTest() throws Exception {
        var request = GetMemoryRequest.newBuilder().setUserId(1).setOtherUserId(2).build();
        CountDownLatch latch = new CountDownLatch(1);
        stub.getMemory(request, new StreamObserver<>() {
            @Override
            public void onNext(Memory value) {
                logger.info(value.toString());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                logger.warning(t.getMessage());
                Assertions.fail();
            }

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
                latch.countDown();
            }
        });
        latch.await();
        logger.info("test finished");
    }

    @Test
    public void getNewMessageTest() throws Exception {
        stub.getNewMessageStream(Hello.newBuilder().build(), new StreamObserver<>(){

            @Override
            public void onNext(Message value) {
                logger.info(value.getText());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
        Thread.sleep(10000);
    }
}
