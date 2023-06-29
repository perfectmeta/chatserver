// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

/**
 * Protobuf type {@code UpdateSelfInfoResponse}
 */
public final class UpdateSelfInfoResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:UpdateSelfInfoResponse)
    UpdateSelfInfoResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UpdateSelfInfoResponse.newBuilder() to construct.
  private UpdateSelfInfoResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UpdateSelfInfoResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new UpdateSelfInfoResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private UpdateSelfInfoResponse(
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

            result_ = input.readInt32();
            break;
          }
          case 18: {
            chatserver.gen.Contact.Builder subBuilder = null;
            if (contact_ != null) {
              subBuilder = contact_.toBuilder();
            }
            contact_ = input.readMessage(chatserver.gen.Contact.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(contact_);
              contact_ = subBuilder.buildPartial();
            }

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
    return chatserver.gen.Chat.internal_static_UpdateSelfInfoResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return chatserver.gen.Chat.internal_static_UpdateSelfInfoResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            chatserver.gen.UpdateSelfInfoResponse.class, chatserver.gen.UpdateSelfInfoResponse.Builder.class);
  }

  public static final int RESULT_FIELD_NUMBER = 1;
  private int result_;
  /**
   * <code>int32 result = 1;</code>
   * @return The result.
   */
  @java.lang.Override
  public int getResult() {
    return result_;
  }

  public static final int CONTACT_FIELD_NUMBER = 2;
  private chatserver.gen.Contact contact_;
  /**
   * <code>.Contact contact = 2;</code>
   * @return Whether the contact field is set.
   */
  @java.lang.Override
  public boolean hasContact() {
    return contact_ != null;
  }
  /**
   * <code>.Contact contact = 2;</code>
   * @return The contact.
   */
  @java.lang.Override
  public chatserver.gen.Contact getContact() {
    return contact_ == null ? chatserver.gen.Contact.getDefaultInstance() : contact_;
  }
  /**
   * <code>.Contact contact = 2;</code>
   */
  @java.lang.Override
  public chatserver.gen.ContactOrBuilder getContactOrBuilder() {
    return getContact();
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
    if (result_ != 0) {
      output.writeInt32(1, result_);
    }
    if (contact_ != null) {
      output.writeMessage(2, getContact());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (result_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, result_);
    }
    if (contact_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getContact());
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
    if (!(obj instanceof chatserver.gen.UpdateSelfInfoResponse)) {
      return super.equals(obj);
    }
    chatserver.gen.UpdateSelfInfoResponse other = (chatserver.gen.UpdateSelfInfoResponse) obj;

    if (getResult()
        != other.getResult()) return false;
    if (hasContact() != other.hasContact()) return false;
    if (hasContact()) {
      if (!getContact()
          .equals(other.getContact())) return false;
    }
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
    hash = (37 * hash) + RESULT_FIELD_NUMBER;
    hash = (53 * hash) + getResult();
    if (hasContact()) {
      hash = (37 * hash) + CONTACT_FIELD_NUMBER;
      hash = (53 * hash) + getContact().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.UpdateSelfInfoResponse parseFrom(
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
  public static Builder newBuilder(chatserver.gen.UpdateSelfInfoResponse prototype) {
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
   * Protobuf type {@code UpdateSelfInfoResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:UpdateSelfInfoResponse)
      chatserver.gen.UpdateSelfInfoResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return chatserver.gen.Chat.internal_static_UpdateSelfInfoResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return chatserver.gen.Chat.internal_static_UpdateSelfInfoResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              chatserver.gen.UpdateSelfInfoResponse.class, chatserver.gen.UpdateSelfInfoResponse.Builder.class);
    }

    // Construct using chatserver.gen.UpdateSelfInfoResponse.newBuilder()
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
      result_ = 0;

      if (contactBuilder_ == null) {
        contact_ = null;
      } else {
        contact_ = null;
        contactBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return chatserver.gen.Chat.internal_static_UpdateSelfInfoResponse_descriptor;
    }

    @java.lang.Override
    public chatserver.gen.UpdateSelfInfoResponse getDefaultInstanceForType() {
      return chatserver.gen.UpdateSelfInfoResponse.getDefaultInstance();
    }

    @java.lang.Override
    public chatserver.gen.UpdateSelfInfoResponse build() {
      chatserver.gen.UpdateSelfInfoResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public chatserver.gen.UpdateSelfInfoResponse buildPartial() {
      chatserver.gen.UpdateSelfInfoResponse result = new chatserver.gen.UpdateSelfInfoResponse(this);
      result.result_ = result_;
      if (contactBuilder_ == null) {
        result.contact_ = contact_;
      } else {
        result.contact_ = contactBuilder_.build();
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
      if (other instanceof chatserver.gen.UpdateSelfInfoResponse) {
        return mergeFrom((chatserver.gen.UpdateSelfInfoResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(chatserver.gen.UpdateSelfInfoResponse other) {
      if (other == chatserver.gen.UpdateSelfInfoResponse.getDefaultInstance()) return this;
      if (other.getResult() != 0) {
        setResult(other.getResult());
      }
      if (other.hasContact()) {
        mergeContact(other.getContact());
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
      chatserver.gen.UpdateSelfInfoResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (chatserver.gen.UpdateSelfInfoResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int result_ ;
    /**
     * <code>int32 result = 1;</code>
     * @return The result.
     */
    @java.lang.Override
    public int getResult() {
      return result_;
    }
    /**
     * <code>int32 result = 1;</code>
     * @param value The result to set.
     * @return This builder for chaining.
     */
    public Builder setResult(int value) {
      
      result_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 result = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearResult() {
      
      result_ = 0;
      onChanged();
      return this;
    }

    private chatserver.gen.Contact contact_;
    private com.google.protobuf.SingleFieldBuilderV3<
        chatserver.gen.Contact, chatserver.gen.Contact.Builder, chatserver.gen.ContactOrBuilder> contactBuilder_;
    /**
     * <code>.Contact contact = 2;</code>
     * @return Whether the contact field is set.
     */
    public boolean hasContact() {
      return contactBuilder_ != null || contact_ != null;
    }
    /**
     * <code>.Contact contact = 2;</code>
     * @return The contact.
     */
    public chatserver.gen.Contact getContact() {
      if (contactBuilder_ == null) {
        return contact_ == null ? chatserver.gen.Contact.getDefaultInstance() : contact_;
      } else {
        return contactBuilder_.getMessage();
      }
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public Builder setContact(chatserver.gen.Contact value) {
      if (contactBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        contact_ = value;
        onChanged();
      } else {
        contactBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public Builder setContact(
        chatserver.gen.Contact.Builder builderForValue) {
      if (contactBuilder_ == null) {
        contact_ = builderForValue.build();
        onChanged();
      } else {
        contactBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public Builder mergeContact(chatserver.gen.Contact value) {
      if (contactBuilder_ == null) {
        if (contact_ != null) {
          contact_ =
            chatserver.gen.Contact.newBuilder(contact_).mergeFrom(value).buildPartial();
        } else {
          contact_ = value;
        }
        onChanged();
      } else {
        contactBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public Builder clearContact() {
      if (contactBuilder_ == null) {
        contact_ = null;
        onChanged();
      } else {
        contact_ = null;
        contactBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public chatserver.gen.Contact.Builder getContactBuilder() {
      
      onChanged();
      return getContactFieldBuilder().getBuilder();
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    public chatserver.gen.ContactOrBuilder getContactOrBuilder() {
      if (contactBuilder_ != null) {
        return contactBuilder_.getMessageOrBuilder();
      } else {
        return contact_ == null ?
            chatserver.gen.Contact.getDefaultInstance() : contact_;
      }
    }
    /**
     * <code>.Contact contact = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        chatserver.gen.Contact, chatserver.gen.Contact.Builder, chatserver.gen.ContactOrBuilder> 
        getContactFieldBuilder() {
      if (contactBuilder_ == null) {
        contactBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            chatserver.gen.Contact, chatserver.gen.Contact.Builder, chatserver.gen.ContactOrBuilder>(
                getContact(),
                getParentForChildren(),
                isClean());
        contact_ = null;
      }
      return contactBuilder_;
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


    // @@protoc_insertion_point(builder_scope:UpdateSelfInfoResponse)
  }

  // @@protoc_insertion_point(class_scope:UpdateSelfInfoResponse)
  private static final chatserver.gen.UpdateSelfInfoResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new chatserver.gen.UpdateSelfInfoResponse();
  }

  public static chatserver.gen.UpdateSelfInfoResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UpdateSelfInfoResponse>
      PARSER = new com.google.protobuf.AbstractParser<UpdateSelfInfoResponse>() {
    @java.lang.Override
    public UpdateSelfInfoResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new UpdateSelfInfoResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UpdateSelfInfoResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UpdateSelfInfoResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public chatserver.gen.UpdateSelfInfoResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

