// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

/**
 * Protobuf type {@code MessageList}
 */
public final class MessageList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:MessageList)
    MessageListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MessageList.newBuilder() to construct.
  private MessageList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MessageList() {
    messageList_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MessageList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MessageList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              messageList_ = new java.util.ArrayList<chatserver.gen.Message>();
              mutable_bitField0_ |= 0x00000001;
            }
            messageList_.add(
                input.readMessage(chatserver.gen.Message.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        messageList_ = java.util.Collections.unmodifiableList(messageList_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return chatserver.gen.Chat.internal_static_MessageList_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return chatserver.gen.Chat.internal_static_MessageList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            chatserver.gen.MessageList.class, chatserver.gen.MessageList.Builder.class);
  }

  public static final int MESSAGELIST_FIELD_NUMBER = 1;
  private java.util.List<chatserver.gen.Message> messageList_;
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  @java.lang.Override
  public java.util.List<chatserver.gen.Message> getMessageListList() {
    return messageList_;
  }
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends chatserver.gen.MessageOrBuilder> 
      getMessageListOrBuilderList() {
    return messageList_;
  }
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  @java.lang.Override
  public int getMessageListCount() {
    return messageList_.size();
  }
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  @java.lang.Override
  public chatserver.gen.Message getMessageList(int index) {
    return messageList_.get(index);
  }
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  @java.lang.Override
  public chatserver.gen.MessageOrBuilder getMessageListOrBuilder(
      int index) {
    return messageList_.get(index);
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
    for (int i = 0; i < messageList_.size(); i++) {
      output.writeMessage(1, messageList_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < messageList_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, messageList_.get(i));
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
    if (!(obj instanceof chatserver.gen.MessageList)) {
      return super.equals(obj);
    }
    chatserver.gen.MessageList other = (chatserver.gen.MessageList) obj;

    if (!getMessageListList()
        .equals(other.getMessageListList())) return false;
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
    if (getMessageListCount() > 0) {
      hash = (37 * hash) + MESSAGELIST_FIELD_NUMBER;
      hash = (53 * hash) + getMessageListList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static chatserver.gen.MessageList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.MessageList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.MessageList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.MessageList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.MessageList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.MessageList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.MessageList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.MessageList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.MessageList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static chatserver.gen.MessageList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.MessageList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.MessageList parseFrom(
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
  public static Builder newBuilder(chatserver.gen.MessageList prototype) {
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
   * Protobuf type {@code MessageList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MessageList)
      chatserver.gen.MessageListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return chatserver.gen.Chat.internal_static_MessageList_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return chatserver.gen.Chat.internal_static_MessageList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              chatserver.gen.MessageList.class, chatserver.gen.MessageList.Builder.class);
    }

    // Construct using chatserver.gen.MessageList.newBuilder()
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
        getMessageListFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (messageListBuilder_ == null) {
        messageList_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        messageListBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return chatserver.gen.Chat.internal_static_MessageList_descriptor;
    }

    @java.lang.Override
    public chatserver.gen.MessageList getDefaultInstanceForType() {
      return chatserver.gen.MessageList.getDefaultInstance();
    }

    @java.lang.Override
    public chatserver.gen.MessageList build() {
      chatserver.gen.MessageList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public chatserver.gen.MessageList buildPartial() {
      chatserver.gen.MessageList result = new chatserver.gen.MessageList(this);
      int from_bitField0_ = bitField0_;
      if (messageListBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          messageList_ = java.util.Collections.unmodifiableList(messageList_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.messageList_ = messageList_;
      } else {
        result.messageList_ = messageListBuilder_.build();
      }
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
      if (other instanceof chatserver.gen.MessageList) {
        return mergeFrom((chatserver.gen.MessageList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(chatserver.gen.MessageList other) {
      if (other == chatserver.gen.MessageList.getDefaultInstance()) return this;
      if (messageListBuilder_ == null) {
        if (!other.messageList_.isEmpty()) {
          if (messageList_.isEmpty()) {
            messageList_ = other.messageList_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMessageListIsMutable();
            messageList_.addAll(other.messageList_);
          }
          onChanged();
        }
      } else {
        if (!other.messageList_.isEmpty()) {
          if (messageListBuilder_.isEmpty()) {
            messageListBuilder_.dispose();
            messageListBuilder_ = null;
            messageList_ = other.messageList_;
            bitField0_ = (bitField0_ & ~0x00000001);
            messageListBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getMessageListFieldBuilder() : null;
          } else {
            messageListBuilder_.addAllMessages(other.messageList_);
          }
        }
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
      chatserver.gen.MessageList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (chatserver.gen.MessageList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<chatserver.gen.Message> messageList_ =
      java.util.Collections.emptyList();
    private void ensureMessageListIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        messageList_ = new java.util.ArrayList<chatserver.gen.Message>(messageList_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        chatserver.gen.Message, chatserver.gen.Message.Builder, chatserver.gen.MessageOrBuilder> messageListBuilder_;

    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public java.util.List<chatserver.gen.Message> getMessageListList() {
      if (messageListBuilder_ == null) {
        return java.util.Collections.unmodifiableList(messageList_);
      } else {
        return messageListBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public int getMessageListCount() {
      if (messageListBuilder_ == null) {
        return messageList_.size();
      } else {
        return messageListBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public chatserver.gen.Message getMessageList(int index) {
      if (messageListBuilder_ == null) {
        return messageList_.get(index);
      } else {
        return messageListBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder setMessageList(
        int index, chatserver.gen.Message value) {
      if (messageListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMessageListIsMutable();
        messageList_.set(index, value);
        onChanged();
      } else {
        messageListBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder setMessageList(
        int index, chatserver.gen.Message.Builder builderForValue) {
      if (messageListBuilder_ == null) {
        ensureMessageListIsMutable();
        messageList_.set(index, builderForValue.build());
        onChanged();
      } else {
        messageListBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder addMessageList(chatserver.gen.Message value) {
      if (messageListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMessageListIsMutable();
        messageList_.add(value);
        onChanged();
      } else {
        messageListBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder addMessageList(
        int index, chatserver.gen.Message value) {
      if (messageListBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMessageListIsMutable();
        messageList_.add(index, value);
        onChanged();
      } else {
        messageListBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder addMessageList(
        chatserver.gen.Message.Builder builderForValue) {
      if (messageListBuilder_ == null) {
        ensureMessageListIsMutable();
        messageList_.add(builderForValue.build());
        onChanged();
      } else {
        messageListBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder addMessageList(
        int index, chatserver.gen.Message.Builder builderForValue) {
      if (messageListBuilder_ == null) {
        ensureMessageListIsMutable();
        messageList_.add(index, builderForValue.build());
        onChanged();
      } else {
        messageListBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder addAllMessageList(
        java.lang.Iterable<? extends chatserver.gen.Message> values) {
      if (messageListBuilder_ == null) {
        ensureMessageListIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, messageList_);
        onChanged();
      } else {
        messageListBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder clearMessageList() {
      if (messageListBuilder_ == null) {
        messageList_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        messageListBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public Builder removeMessageList(int index) {
      if (messageListBuilder_ == null) {
        ensureMessageListIsMutable();
        messageList_.remove(index);
        onChanged();
      } else {
        messageListBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public chatserver.gen.Message.Builder getMessageListBuilder(
        int index) {
      return getMessageListFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public chatserver.gen.MessageOrBuilder getMessageListOrBuilder(
        int index) {
      if (messageListBuilder_ == null) {
        return messageList_.get(index);  } else {
        return messageListBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public java.util.List<? extends chatserver.gen.MessageOrBuilder> 
         getMessageListOrBuilderList() {
      if (messageListBuilder_ != null) {
        return messageListBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(messageList_);
      }
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public chatserver.gen.Message.Builder addMessageListBuilder() {
      return getMessageListFieldBuilder().addBuilder(
          chatserver.gen.Message.getDefaultInstance());
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public chatserver.gen.Message.Builder addMessageListBuilder(
        int index) {
      return getMessageListFieldBuilder().addBuilder(
          index, chatserver.gen.Message.getDefaultInstance());
    }
    /**
     * <code>repeated .Message messageList = 1;</code>
     */
    public java.util.List<chatserver.gen.Message.Builder> 
         getMessageListBuilderList() {
      return getMessageListFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        chatserver.gen.Message, chatserver.gen.Message.Builder, chatserver.gen.MessageOrBuilder> 
        getMessageListFieldBuilder() {
      if (messageListBuilder_ == null) {
        messageListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            chatserver.gen.Message, chatserver.gen.Message.Builder, chatserver.gen.MessageOrBuilder>(
                messageList_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        messageList_ = null;
      }
      return messageListBuilder_;
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


    // @@protoc_insertion_point(builder_scope:MessageList)
  }

  // @@protoc_insertion_point(class_scope:MessageList)
  private static final chatserver.gen.MessageList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new chatserver.gen.MessageList();
  }

  public static chatserver.gen.MessageList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MessageList>
      PARSER = new com.google.protobuf.AbstractParser<MessageList>() {
    @java.lang.Override
    public MessageList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MessageList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MessageList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MessageList> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public chatserver.gen.MessageList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

