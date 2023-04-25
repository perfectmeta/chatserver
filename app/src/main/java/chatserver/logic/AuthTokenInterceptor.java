package chatserver.logic;

import chatserver.controller.UserController;
import chatserver.dao.User;
import io.grpc.*;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall.Listener;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcGlobalInterceptor
public class AuthTokenInterceptor implements ServerInterceptor {

    @Autowired
    private UserController users;  // 不够service层了，直接用repo

    public static final Context.Key<User> USER = Context.key("user");


    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall,
                                                      final Metadata metadata,
                                                      final ServerCallHandler<ReqT, RespT> serverCallHandler) {

        final String authToken = metadata.get(Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER));

        final User user = validate(authToken);

        if (user == null) {
            serverCall.close(Status.PERMISSION_DENIED, new Metadata());
            return new ServerCall.Listener<>() {
            };
        }

        Context context = Context.current().withValue(USER, user);

        return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
    }

    private User validate(String authToken) {
        return users.findByUserId(Long.parseLong(authToken));
    }
}