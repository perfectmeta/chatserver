package chatserver.logic;

import chatserver.service.UserService;
import chatserver.entity.User;
import chatserver.userjob.UserBlackboard;
import chatserver.userjob.Variables;
import io.grpc.*;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall.Listener;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@GRpcGlobalInterceptor
public class AuthTokenInterceptor implements ServerInterceptor {
    private static final Logger logger = Logger.getLogger(AuthTokenInterceptor.class.getName());

    private final UserService users;

    public static final Context.Key<User> USER = Context.key("user");
    public static final Context.Key<Variables> VARIABLES = Context.key("variables");
    public static final Context.Key<UserBlackboard> BLACKBOARD = Context.key("blackboard");

    @Autowired
    public AuthTokenInterceptor(UserService users) {
        this.users = users;
    }


    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall,
                                                      final Metadata metadata,
                                                      final ServerCallHandler<ReqT, RespT> serverCallHandler) {

        MethodDescriptor<ReqT, RespT> methodDescriptor = serverCall.getMethodDescriptor();
        var methodName = methodDescriptor.getFullMethodName();
        if (ignoreValidate(methodName) || USER.get() != null) {
            logger.info("request handler " + methodName + "ignore validation");
            return serverCallHandler.startCall(serverCall, metadata);
        }
        final String authToken = metadata.get(Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER));
        User user = null;
        try {
            user = validate(authToken);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            // e.printStackTrace();
        }

        if (user == null) {
            logger.warning("validation failed when access " + methodName + " token " + authToken);
            serverCall.close(Status.PERMISSION_DENIED, new Metadata());
            return new ServerCall.Listener<>() {};
        }
        logger.info("validation success when access " + methodName);

        var variables = new Variables();
        variables.put("username", user.getNickName());

        Context context = Context.current()
                .withValue(USER, user)
                .withValue(BLACKBOARD, new UserBlackboard())
                .withValue(VARIABLES, variables);

        ServerCall<ReqT, RespT> wrappedCall = new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
            @Override
            public void close(Status status, Metadata metadata) {
                // todo: clean userjob
                logger.warning("client close");
                super.close(status, metadata);
            }
        };

        return Contexts.interceptCall(context, wrappedCall, metadata, serverCallHandler);
    }

    private User validate(String authToken) {
        return users.findByUserId(Long.parseLong(authToken));
    }

    private boolean ignoreValidate(String methodName) {
        return switch (methodName) {
            case "ChatService/Signup" -> true;
            default -> false;
        };
    }
}