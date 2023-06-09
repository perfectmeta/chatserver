// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

/**
 * Protobuf type {@code EnterRoomRequest}
 */
public final class EnterRoomRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:EnterRoomRequest)
    EnterRoomRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EnterRoomRequest.newBuilder() to construct.
  private EnterRoomRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EnterRoomRequest() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new EnterRoomRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private EnterRoomRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            roomId_ = input.readInt64();
            break;
          }
          case 16: {

            lastMessageId_ = input.readInt64();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return chatserver.gen.Chat.internal_static_EnterRoomRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return chatserver.gen.Chat.internal_static_EnterRoomRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            chatserver.gen.EnterRoomRequest.class, chatserver.gen.EnterRoomRequest.Builder.class);
  }

  public static final int ROOMID_FIELD_NUMBER = 1;
  private long roomId_;
  /**
   * <code>int64 roomId = 1;</code>
   * @return The roomId.
   */
  @java.lang.Override
  public long getRoomId() {
    return roomId_;
  }

  public static final int LASTMESSAGEID_FIELD_NUMBER = 2;
  private long lastMessageId_;
  /**
   * <code>int64 lastMessageId = 2;</code>
   * @return The lastMessageId.
   */
  @java.lang.Override
  public long getLastMessageId() {
    return lastMessageId_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (roomId_ != 0L) {
      output.writeInt64(1, roomId_);
    }
    if (lastMessageId_ != 0L) {
      output.writeInt64(2, lastMessageId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (roomId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, roomId_);
    }
    if (lastMessageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, lastMessageId_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof chatserver.gen.EnterRoomRequest)) {
      return super.equals(obj);
    }
    chatserver.gen.EnterRoomRequest other = (chatserver.gen.EnterRoomRequest) obj;

    if (getRoomId()
        != other.getRoomId()) return false;
    if (getLastMessageId()
        != other.getLastMessageId()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ROOMID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getRoomId());
    hash = (37 * hash) + LASTMESSAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getLastMessageId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static chatserver.gen.EnterRoomRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.EnterRoomRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static chatserver.gen.EnterRoomRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.EnterRoomRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(chatserver.gen.EnterRoomRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code EnterRoomRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:EnterRoomRequest)
      chatserver.gen.EnterRoomRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return chatserver.gen.Chat.internal_static_EnterRoomRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return chatserver.gen.Chat.internal_static_EnterRoomRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              chatserver.gen.EnterRoomRequest.class, chatserver.gen.EnterRoomRequest.Builder.class);
    }

    // Construct using chatserver.gen.EnterRoomRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      roomId_ = 0L;

      lastMessageId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return chatserver.gen.Chat.internal_static_EnterRoomRequest_descriptor;
    }

    @java.lang.Override
    public chatserver.gen.EnterRoomRequest getDefaultInstanceForType() {
      return chatserver.gen.EnterRoomRequest.getDefaultInstance();
    }

    @java.lang.Override
    public chatserver.gen.EnterRoomRequest build() {
      chatserver.gen.EnterRoomRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public chatserver.gen.EnterRoomRequest buildPartial() {
      chatserver.gen.EnterRoomRequest result = new chatserver.gen.EnterRoomRequest(this);
      result.roomId_ = roomId_;
      result.lastMessageId_ = lastMessageId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof chatserver.gen.EnterRoomRequest) {
        return mergeFrom((chatserver.gen.EnterRoomRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(chatserver.gen.EnterRoomRequest other) {
      if (other == chatserver.gen.EnterRoomRequest.getDefaultInstance()) return this;
      if (other.getRoomId() != 0L) {
        setRoomId(other.getRoomId());
      }
      if (other.getLastMessageId() != 0L) {
        setLastMessageId(other.getLastMessageId());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      chatserver.gen.EnterRoomRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (chatserver.gen.EnterRoomRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long roomId_ ;
    /**
     * <code>int64 roomId = 1;</code>
     * @return The roomId.
     */
    @java.lang.Override
    public long getRoomId() {
      return roomId_;
    }
    /**
     * <code>int64 roomId = 1;</code>
     * @param value The roomId to set.
     * @return This builder for chaining.
     */
    public Builder setRoomId(long value) {
      
      roomId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 roomId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRoomId() {
      
      roomId_ = 0L;
      onChanged();
      return this;
    }

    private long lastMessageId_ ;
    /**
     * <code>int64 lastMessageId = 2;</code>
     * @return The lastMessageId.
     */
    @java.lang.Override
    public long getLastMessageId() {
      return lastMessageId_;
    }
    /**
     * <code>int64 lastMessageId = 2;</code>
     * @param value The lastMessageId to set.
     * @return This builder for chaining.
     */
    public Builder setLastMessageId(long value) {
      
      lastMessageId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 lastMessageId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearLastMessageId() {
      
      lastMessageId_ = 0L;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:EnterRoomRequest)
  }

  // @@protoc_insertion_point(class_scope:EnterRoomRequest)
  private static final chatserver.gen.EnterRoomRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new chatserver.gen.EnterRoomRequest();
  }

  public static chatserver.gen.EnterRoomRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EnterRoomRequest>
      PARSER = new com.google.protobuf.AbstractParser<EnterRoomRequest>() {
    @java.lang.Override
    public EnterRoomRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new EnterRoomRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<EnterRoomRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<EnterRoomRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public chatserver.gen.EnterRoomRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

