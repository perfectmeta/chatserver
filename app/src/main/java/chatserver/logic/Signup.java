package chatserver.logic;

import chatserver.config.ConfigManager;
import chatserver.config.RobotConfig;
import chatserver.entity.*;
import chatserver.gen.RegisterFeedback;
import chatserver.gen.RegisterInfo;
import chatserver.logic.internal.SignupBot;
import chatserver.service.ContactService;
import chatserver.service.RoomService;
import chatserver.service.UserService;
import chatserver.util.StringUtil;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Signup {
    private static final Logger logger = Logger.getLogger(Signup.class.getName());
    private final UserService userService;
    private final ContactService contactService;
    private final RoomService roomService;
    private final SignupBot signupBotServer;

    public Signup(UserService userService,
                  ContactService contactService,
                  RoomService roomService,
                  SignupBot signupBotServer) {
        this.userService = userService;
        this.contactService = contactService;
        this.roomService = roomService;
        this.signupBotServer = signupBotServer;
    }

    @Transactional
    public void run(RegisterInfo request, StreamObserver<RegisterFeedback> responseObserver) {
        var email = request.getEmail();
        var phone = request.getPhone();
        var nickName = request.getNickname();


        User dbUser = userService.findByNickName(nickName);
        if (dbUser != null) {
            var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                    .setUserId((int)dbUser.getUserId()).build();
            logger.info("signin by nickname: " + nickName + " token: " + dbUser.getUserId());
            responseObserver.onNext(feedback);
            responseObserver.onCompleted();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUserType(EUserType.HUMAN);    // 人类类别为1
        user.setPhone(phone);
        user.setNickName(nickName);
        if ((dbUser=userService.addUser(user)) == null) {
            var res = RegisterFeedback.newBuilder()
                    .setStatusCode(RegisterFeedback.StatusCode.OTHER_ERROR_VALUE)
                    .setMessage("DB error").build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            logger.warning("Signup failed since db error, nick name: " + nickName);
            return;
        }
        var feedback = RegisterFeedback.newBuilder().setStatusCode(RegisterFeedback.StatusCode.OK_VALUE)
                        .setUserId((int)dbUser.getUserId()).build();
        signupBotAndMakeContact(dbUser);
        responseObserver.onNext(feedback);
        responseObserver.onCompleted();
        logger.info("Signup success register nickname " + nickName + " token " + dbUser.getUserId());
    }

    private void signupBotAndMakeContact(User user) {
        var robots = ConfigManager.getInstance().getRobots().values();
        for (var robot : robots) {
            User botUser = signupBotServer.signupFor(robot);
            contactService.addContact(botUser.getUserId(), user.getUserId());
            contactService.addContact(user.getUserId(), botUser.getUserId());
            Room newRoom = makeRoom(robot, user, botUser);
            fillFirstMessage(newRoom.getRoomId(), botUser.getUserId(), user.getNickName(), robot);
        }
    }

    private List<Contact> getAllContact(long userId) {
        return contactService.findBySubjectUserId(userId);
    }

    private Room makeRoom(RobotConfig robot, User user, User aiUser) {
        Room newRoom = new Room();
        newRoom.setRoomName(robot.getId() + "'s room");
        newRoom.setUserType(EUserType.HUMAN);
        newRoom.setUserId(user.getUserId());
        newRoom.setUserShowName(user.getNickName());
        newRoom.setAiType(EUserType.BOT);
        newRoom.setAiUserId(aiUser.getUserId());
        newRoom.setAiShowName(robot.getId());
        newRoom.setCreatedTime(System.currentTimeMillis());
        newRoom.setFirstMessageId(-1);
        newRoom.setLastMessageId(-1);
        return roomService.upsertRoom(newRoom);
    }


    private void fillFirstMessage(long roomId, long userId, String userName, RobotConfig robot) {
        var tempVariables = new HashMap<String, String>();
        tempVariables.put("botname", robot.getId());
        tempVariables.put("username", userName);
        Message message = new Message();
        message.setRoomId(roomId);
        message.setCreatedTime(System.currentTimeMillis());
        message.setMsgType(EMsgType.TEXT);
        message.setAuthorUserId(userId);
        message.setAuthorShowName(robot.getId());
        message.setAuthorUserType(EUserType.BOT);
        message.setText(StringUtil.fillVariable(robot.getGreeting(), tempVariables));
        roomService.addMessage(message);
    }
}
