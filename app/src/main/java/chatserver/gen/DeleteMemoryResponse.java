// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

/**
 * Protobuf type {@code DeleteMemoryResponse}
 */
public final class DeleteMemoryResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:DeleteMemoryResponse)
    DeleteMemoryResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DeleteMemoryResponse.newBuilder() to construct.
  private DeleteMemoryResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DeleteMemoryResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DeleteMemoryResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private DeleteMemoryResponse(
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

            errorCode_ = input.readInt32();
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
    return chatserver.gen.Chat.internal_static_DeleteMemoryResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return chatserver.gen.Chat.internal_static_DeleteMemoryResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            chatserver.gen.DeleteMemoryResponse.class, chatserver.gen.DeleteMemoryResponse.Builder.class);
  }

  public static final int ERRORCODE_FIELD_NUMBER = 1;
  private int errorCode_;
  /**
   * <code>int32 errorCode = 1;</code>
   * @return The errorCode.
   */
  @java.lang.Override
  public int getErrorCode() {
    return errorCode_;
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
    if (errorCode_ != 0) {
      output.writeInt32(1, errorCode_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (errorCode_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, errorCode_);
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
    if (!(obj instanceof chatserver.gen.DeleteMemoryResponse)) {
      return super.equals(obj);
    }
    chatserver.gen.DeleteMemoryResponse other = (chatserver.gen.DeleteMemoryResponse) obj;

    if (getErrorCode()
        != other.getErrorCode()) return false;
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
    hash = (37 * hash) + ERRORCODE_FIELD_NUMBER;
    hash = (53 * hash) + getErrorCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.DeleteMemoryResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static chatserver.gen.DeleteMemoryResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.DeleteMemoryResponse parseFrom(
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
  public static Builder newBuilder(chatserver.gen.DeleteMemoryResponse prototype) {
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
   * Protobuf type {@code DeleteMemoryResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:DeleteMemoryResponse)
      chatserver.gen.DeleteMemoryResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return chatserver.gen.Chat.internal_static_DeleteMemoryResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return chatserver.gen.Chat.internal_static_DeleteMemoryResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              chatserver.gen.DeleteMemoryResponse.class, chatserver.gen.DeleteMemoryResponse.Builder.class);
    }

    // Construct using chatserver.gen.DeleteMemoryResponse.newBuilder()
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
      errorCode_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return chatserver.gen.Chat.internal_static_DeleteMemoryResponse_descriptor;
    }

    @java.lang.Override
    public chatserver.gen.DeleteMemoryResponse getDefaultInstanceForType() {
      return chatserver.gen.DeleteMemoryResponse.getDefaultInstance();
    }

    @java.lang.Override
    public chatserver.gen.DeleteMemoryResponse build() {
      chatserver.gen.DeleteMemoryResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public chatserver.gen.DeleteMemoryResponse buildPartial() {
      chatserver.gen.DeleteMemoryResponse result = new chatserver.gen.DeleteMemoryResponse(this);
      result.errorCode_ = errorCode_;
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
      if (other instanceof chatserver.gen.DeleteMemoryResponse) {
        return mergeFrom((chatserver.gen.DeleteMemoryResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(chatserver.gen.DeleteMemoryResponse other) {
      if (other == chatserver.gen.DeleteMemoryResponse.getDefaultInstance()) return this;
      if (other.getErrorCode() != 0) {
        setErrorCode(other.getErrorCode());
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
      chatserver.gen.DeleteMemoryResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (chatserver.gen.DeleteMemoryResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int errorCode_ ;
    /**
     * <code>int32 errorCode = 1;</code>
     * @return The errorCode.
     */
    @java.lang.Override
    public int getErrorCode() {
      return errorCode_;
    }
    /**
     * <code>int32 errorCode = 1;</code>
     * @param value The errorCode to set.
     * @return This builder for chaining.
     */
    public Builder setErrorCode(int value) {
      
      errorCode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 errorCode = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearErrorCode() {
      
      errorCode_ = 0;
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


    // @@protoc_insertion_point(builder_scope:DeleteMemoryResponse)
  }

  // @@protoc_insertion_point(class_scope:DeleteMemoryResponse)
  private static final chatserver.gen.DeleteMemoryResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new chatserver.gen.DeleteMemoryResponse();
  }

  public static chatserver.gen.DeleteMemoryResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DeleteMemoryResponse>
      PARSER = new com.google.protobuf.AbstractParser<DeleteMemoryResponse>() {
    @java.lang.Override
    public DeleteMemoryResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new DeleteMemoryResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<DeleteMemoryResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DeleteMemoryResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public chatserver.gen.DeleteMemoryResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

