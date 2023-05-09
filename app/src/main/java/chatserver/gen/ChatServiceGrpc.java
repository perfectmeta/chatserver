package chatserver.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.1)",
    comments = "Source: chat.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final String SERVICE_NAME = "ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<chatserver.gen.RegisterInfo,
      chatserver.gen.RegisterFeedback> getSignupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Signup",
      requestType = chatserver.gen.RegisterInfo.class,
      responseType = chatserver.gen.RegisterFeedback.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chatserver.gen.RegisterInfo,
      chatserver.gen.RegisterFeedback> getSignupMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.RegisterInfo, chatserver.gen.RegisterFeedback> getSignupMethod;
    if ((getSignupMethod = ChatServiceGrpc.getSignupMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getSignupMethod = ChatServiceGrpc.getSignupMethod) == null) {
          ChatServiceGrpc.getSignupMethod = getSignupMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.RegisterInfo, chatserver.gen.RegisterFeedback>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Signup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.RegisterInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.RegisterFeedback.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("Signup"))
              .build();
        }
      }
    }
    return getSignupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chatserver.gen.Hello,
      chatserver.gen.RoomInfo> getGetRoomListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRoomList",
      requestType = chatserver.gen.Hello.class,
      responseType = chatserver.gen.RoomInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<chatserver.gen.Hello,
      chatserver.gen.RoomInfo> getGetRoomListMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.Hello, chatserver.gen.RoomInfo> getGetRoomListMethod;
    if ((getGetRoomListMethod = ChatServiceGrpc.getGetRoomListMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getGetRoomListMethod = ChatServiceGrpc.getGetRoomListMethod) == null) {
          ChatServiceGrpc.getGetRoomListMethod = getGetRoomListMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.Hello, chatserver.gen.RoomInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRoomList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.Hello.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.RoomInfo.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("GetRoomList"))
              .build();
        }
      }
    }
    return getGetRoomListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chatserver.gen.Hello,
      chatserver.gen.Message> getGetNewMessageStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNewMessageStream",
      requestType = chatserver.gen.Hello.class,
      responseType = chatserver.gen.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<chatserver.gen.Hello,
      chatserver.gen.Message> getGetNewMessageStreamMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.Hello, chatserver.gen.Message> getGetNewMessageStreamMethod;
    if ((getGetNewMessageStreamMethod = ChatServiceGrpc.getGetNewMessageStreamMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getGetNewMessageStreamMethod = ChatServiceGrpc.getGetNewMessageStreamMethod) == null) {
          ChatServiceGrpc.getGetNewMessageStreamMethod = getGetNewMessageStreamMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.Hello, chatserver.gen.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNewMessageStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.Hello.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.Message.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("GetNewMessageStream"))
              .build();
        }
      }
    }
    return getGetNewMessageStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chatserver.gen.EnterRoomRequest,
      chatserver.gen.Message> getEnterRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnterRoom",
      requestType = chatserver.gen.EnterRoomRequest.class,
      responseType = chatserver.gen.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<chatserver.gen.EnterRoomRequest,
      chatserver.gen.Message> getEnterRoomMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.EnterRoomRequest, chatserver.gen.Message> getEnterRoomMethod;
    if ((getEnterRoomMethod = ChatServiceGrpc.getEnterRoomMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getEnterRoomMethod = ChatServiceGrpc.getEnterRoomMethod) == null) {
          ChatServiceGrpc.getEnterRoomMethod = getEnterRoomMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.EnterRoomRequest, chatserver.gen.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnterRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.EnterRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.Message.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("EnterRoom"))
              .build();
        }
      }
    }
    return getEnterRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chatserver.gen.AudioStream,
      chatserver.gen.TextStream> getSpeechRecognizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SpeechRecognize",
      requestType = chatserver.gen.AudioStream.class,
      responseType = chatserver.gen.TextStream.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<chatserver.gen.AudioStream,
      chatserver.gen.TextStream> getSpeechRecognizeMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.AudioStream, chatserver.gen.TextStream> getSpeechRecognizeMethod;
    if ((getSpeechRecognizeMethod = ChatServiceGrpc.getSpeechRecognizeMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getSpeechRecognizeMethod = ChatServiceGrpc.getSpeechRecognizeMethod) == null) {
          ChatServiceGrpc.getSpeechRecognizeMethod = getSpeechRecognizeMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.AudioStream, chatserver.gen.TextStream>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SpeechRecognize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.AudioStream.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.TextStream.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("SpeechRecognize"))
              .build();
        }
      }
    }
    return getSpeechRecognizeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chatserver.gen.ChatRequest,
      chatserver.gen.ChatResponseStream> getChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Chat",
      requestType = chatserver.gen.ChatRequest.class,
      responseType = chatserver.gen.ChatResponseStream.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<chatserver.gen.ChatRequest,
      chatserver.gen.ChatResponseStream> getChatMethod() {
    io.grpc.MethodDescriptor<chatserver.gen.ChatRequest, chatserver.gen.ChatResponseStream> getChatMethod;
    if ((getChatMethod = ChatServiceGrpc.getChatMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getChatMethod = ChatServiceGrpc.getChatMethod) == null) {
          ChatServiceGrpc.getChatMethod = getChatMethod =
              io.grpc.MethodDescriptor.<chatserver.gen.ChatRequest, chatserver.gen.ChatResponseStream>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Chat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.ChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chatserver.gen.ChatResponseStream.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("Chat"))
              .build();
        }
      }
    }
    return getChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * 第一步用户登录，取到token后，
     * 把token放到之后所有的请求的header里，例子见app/src/test/java/chatserver/ServerAppTest.java
     * </pre>
     */
    default void signup(chatserver.gen.RegisterInfo request,
        io.grpc.stub.StreamObserver<chatserver.gen.RegisterFeedback> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSignupMethod(), responseObserver);
    }

    /**
     * <pre>
     * 登录游戏后立马就发送Hello，取room list，new message stream，这两个可能就是在连接期间一直会发。
     * </pre>
     */
    default void getRoomList(chatserver.gen.Hello request,
        io.grpc.stub.StreamObserver<chatserver.gen.RoomInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRoomListMethod(), responseObserver);
    }

    /**
     * <pre>
     *自己发和gpt回应的在Chat()里自己确认，不通过这个接口发回自己
     * </pre>
     */
    default void getNewMessageStream(chatserver.gen.Hello request,
        io.grpc.stub.StreamObserver<chatserver.gen.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNewMessageStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * 进入房间，发lastMessageId后，服务器同步给客户端这之后的消息
     * </pre>
     */
    default void enterRoom(chatserver.gen.EnterRoomRequest request,
        io.grpc.stub.StreamObserver<chatserver.gen.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnterRoomMethod(), responseObserver);
    }

    /**
     * <pre>
     * 流式语音识别，这个是不管接下来的chat的
     * </pre>
     */
    default io.grpc.stub.StreamObserver<chatserver.gen.AudioStream> speechRecognize(
        io.grpc.stub.StreamObserver<chatserver.gen.TextStream> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSpeechRecognizeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 聊天，会接受chatgpt发回的文本流，这样响应性更好。当一句话或多句话结束时会服务器会给出语音。
     * </pre>
     */
    default void chat(chatserver.gen.ChatRequest request,
        io.grpc.stub.StreamObserver<chatserver.gen.ChatResponseStream> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getChatMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatService.
   */
  public static abstract class ChatServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatService.
   */
  public static final class ChatServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 第一步用户登录，取到token后，
     * 把token放到之后所有的请求的header里，例子见app/src/test/java/chatserver/ServerAppTest.java
     * </pre>
     */
    public void signup(chatserver.gen.RegisterInfo request,
        io.grpc.stub.StreamObserver<chatserver.gen.RegisterFeedback> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSignupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 登录游戏后立马就发送Hello，取room list，new message stream，这两个可能就是在连接期间一直会发。
     * </pre>
     */
    public void getRoomList(chatserver.gen.Hello request,
        io.grpc.stub.StreamObserver<chatserver.gen.RoomInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetRoomListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *自己发和gpt回应的在Chat()里自己确认，不通过这个接口发回自己
     * </pre>
     */
    public void getNewMessageStream(chatserver.gen.Hello request,
        io.grpc.stub.StreamObserver<chatserver.gen.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetNewMessageStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 进入房间，发lastMessageId后，服务器同步给客户端这之后的消息
     * </pre>
     */
    public void enterRoom(chatserver.gen.EnterRoomRequest request,
        io.grpc.stub.StreamObserver<chatserver.gen.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getEnterRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 流式语音识别，这个是不管接下来的chat的
     * </pre>
     */
    public io.grpc.stub.StreamObserver<chatserver.gen.AudioStream> speechRecognize(
        io.grpc.stub.StreamObserver<chatserver.gen.TextStream> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getSpeechRecognizeMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * 聊天，会接受chatgpt发回的文本流，这样响应性更好。当一句话或多句话结束时会服务器会给出语音。
     * </pre>
     */
    public void chat(chatserver.gen.ChatRequest request,
        io.grpc.stub.StreamObserver<chatserver.gen.ChatResponseStream> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getChatMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatService.
   */
  public static final class ChatServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 第一步用户登录，取到token后，
     * 把token放到之后所有的请求的header里，例子见app/src/test/java/chatserver/ServerAppTest.java
     * </pre>
     */
    public chatserver.gen.RegisterFeedback signup(chatserver.gen.RegisterInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSignupMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 登录游戏后立马就发送Hello，取room list，new message stream，这两个可能就是在连接期间一直会发。
     * </pre>
     */
    public java.util.Iterator<chatserver.gen.RoomInfo> getRoomList(
        chatserver.gen.Hello request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetRoomListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *自己发和gpt回应的在Chat()里自己确认，不通过这个接口发回自己
     * </pre>
     */
    public java.util.Iterator<chatserver.gen.Message> getNewMessageStream(
        chatserver.gen.Hello request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetNewMessageStreamMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 进入房间，发lastMessageId后，服务器同步给客户端这之后的消息
     * </pre>
     */
    public java.util.Iterator<chatserver.gen.Message> enterRoom(
        chatserver.gen.EnterRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getEnterRoomMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 聊天，会接受chatgpt发回的文本流，这样响应性更好。当一句话或多句话结束时会服务器会给出语音。
     * </pre>
     */
    public java.util.Iterator<chatserver.gen.ChatResponseStream> chat(
        chatserver.gen.ChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getChatMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatService.
   */
  public static final class ChatServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 第一步用户登录，取到token后，
     * 把token放到之后所有的请求的header里，例子见app/src/test/java/chatserver/ServerAppTest.java
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<chatserver.gen.RegisterFeedback> signup(
        chatserver.gen.RegisterInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSignupMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SIGNUP = 0;
  private static final int METHODID_GET_ROOM_LIST = 1;
  private static final int METHODID_GET_NEW_MESSAGE_STREAM = 2;
  private static final int METHODID_ENTER_ROOM = 3;
  private static final int METHODID_CHAT = 4;
  private static final int METHODID_SPEECH_RECOGNIZE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SIGNUP:
          serviceImpl.signup((chatserver.gen.RegisterInfo) request,
              (io.grpc.stub.StreamObserver<chatserver.gen.RegisterFeedback>) responseObserver);
          break;
        case METHODID_GET_ROOM_LIST:
          serviceImpl.getRoomList((chatserver.gen.Hello) request,
              (io.grpc.stub.StreamObserver<chatserver.gen.RoomInfo>) responseObserver);
          break;
        case METHODID_GET_NEW_MESSAGE_STREAM:
          serviceImpl.getNewMessageStream((chatserver.gen.Hello) request,
              (io.grpc.stub.StreamObserver<chatserver.gen.Message>) responseObserver);
          break;
        case METHODID_ENTER_ROOM:
          serviceImpl.enterRoom((chatserver.gen.EnterRoomRequest) request,
              (io.grpc.stub.StreamObserver<chatserver.gen.Message>) responseObserver);
          break;
        case METHODID_CHAT:
          serviceImpl.chat((chatserver.gen.ChatRequest) request,
              (io.grpc.stub.StreamObserver<chatserver.gen.ChatResponseStream>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SPEECH_RECOGNIZE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.speechRecognize(
              (io.grpc.stub.StreamObserver<chatserver.gen.TextStream>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSignupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chatserver.gen.RegisterInfo,
              chatserver.gen.RegisterFeedback>(
                service, METHODID_SIGNUP)))
        .addMethod(
          getGetRoomListMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              chatserver.gen.Hello,
              chatserver.gen.RoomInfo>(
                service, METHODID_GET_ROOM_LIST)))
        .addMethod(
          getGetNewMessageStreamMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              chatserver.gen.Hello,
              chatserver.gen.Message>(
                service, METHODID_GET_NEW_MESSAGE_STREAM)))
        .addMethod(
          getEnterRoomMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              chatserver.gen.EnterRoomRequest,
              chatserver.gen.Message>(
                service, METHODID_ENTER_ROOM)))
        .addMethod(
          getSpeechRecognizeMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              chatserver.gen.AudioStream,
              chatserver.gen.TextStream>(
                service, METHODID_SPEECH_RECOGNIZE)))
        .addMethod(
          getChatMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              chatserver.gen.ChatRequest,
              chatserver.gen.ChatResponseStream>(
                service, METHODID_CHAT)))
        .build();
  }

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return chatserver.gen.Chat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getSignupMethod())
              .addMethod(getGetRoomListMethod())
              .addMethod(getGetNewMessageStreamMethod())
              .addMethod(getEnterRoomMethod())
              .addMethod(getSpeechRecognizeMethod())
              .addMethod(getChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
