// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

/**
 * <pre>
 *&#47;////// asr
 * </pre>
 *
 * Protobuf type {@code AudioStream}
 */
public final class AudioStream extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:AudioStream)
    AudioStreamOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AudioStream.newBuilder() to construct.
  private AudioStream(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AudioStream() {
    audio_ = com.google.protobuf.ByteString.EMPTY;
    audioFormat_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AudioStream();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AudioStream(
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
          case 34: {

            audio_ = input.readBytes();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            audioFormat_ = s;
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
    return chatserver.gen.Chat.internal_static_AudioStream_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return chatserver.gen.Chat.internal_static_AudioStream_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            chatserver.gen.AudioStream.class, chatserver.gen.AudioStream.Builder.class);
  }

  public static final int ROOMID_FIELD_NUMBER = 1;
  private long roomId_;
  /**
   * <pre>
   * 传一下roomId吧，可能生成audioUrl会用到。
   * </pre>
   *
   * <code>int64 roomId = 1;</code>
   * @return The roomId.
   */
  @java.lang.Override
  public long getRoomId() {
    return roomId_;
  }

  public static final int AUDIO_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString audio_;
  /**
   * <pre>
   * 麦克风收录产生
   * </pre>
   *
   * <code>bytes audio = 4;</code>
   * @return The audio.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getAudio() {
    return audio_;
  }

  public static final int AUDIOFORMAT_FIELD_NUMBER = 5;
  private volatile java.lang.Object audioFormat_;
  /**
   * <pre>
   * 音频传输格式, 支持列表见ReadMe
   * </pre>
   *
   * <code>string audioFormat = 5;</code>
   * @return The audioFormat.
   */
  @java.lang.Override
  public java.lang.String getAudioFormat() {
    java.lang.Object ref = audioFormat_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      audioFormat_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * 音频传输格式, 支持列表见ReadMe
   * </pre>
   *
   * <code>string audioFormat = 5;</code>
   * @return The bytes for audioFormat.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAudioFormatBytes() {
    java.lang.Object ref = audioFormat_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      audioFormat_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!audio_.isEmpty()) {
      output.writeBytes(4, audio_);
    }
    if (!getAudioFormatBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, audioFormat_);
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
    if (!audio_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, audio_);
    }
    if (!getAudioFormatBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, audioFormat_);
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
    if (!(obj instanceof chatserver.gen.AudioStream)) {
      return super.equals(obj);
    }
    chatserver.gen.AudioStream other = (chatserver.gen.AudioStream) obj;

    if (getRoomId()
        != other.getRoomId()) return false;
    if (!getAudio()
        .equals(other.getAudio())) return false;
    if (!getAudioFormat()
        .equals(other.getAudioFormat())) return false;
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
    hash = (37 * hash) + AUDIO_FIELD_NUMBER;
    hash = (53 * hash) + getAudio().hashCode();
    hash = (37 * hash) + AUDIOFORMAT_FIELD_NUMBER;
    hash = (53 * hash) + getAudioFormat().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static chatserver.gen.AudioStream parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.AudioStream parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.AudioStream parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.AudioStream parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.AudioStream parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static chatserver.gen.AudioStream parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static chatserver.gen.AudioStream parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.AudioStream parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.AudioStream parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static chatserver.gen.AudioStream parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static chatserver.gen.AudioStream parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static chatserver.gen.AudioStream parseFrom(
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
  public static Builder newBuilder(chatserver.gen.AudioStream prototype) {
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
   * <pre>
   *&#47;////// asr
   * </pre>
   *
   * Protobuf type {@code AudioStream}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:AudioStream)
      chatserver.gen.AudioStreamOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return chatserver.gen.Chat.internal_static_AudioStream_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return chatserver.gen.Chat.internal_static_AudioStream_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              chatserver.gen.AudioStream.class, chatserver.gen.AudioStream.Builder.class);
    }

    // Construct using chatserver.gen.AudioStream.newBuilder()
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

      audio_ = com.google.protobuf.ByteString.EMPTY;

      audioFormat_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return chatserver.gen.Chat.internal_static_AudioStream_descriptor;
    }

    @java.lang.Override
    public chatserver.gen.AudioStream getDefaultInstanceForType() {
      return chatserver.gen.AudioStream.getDefaultInstance();
    }

    @java.lang.Override
    public chatserver.gen.AudioStream build() {
      chatserver.gen.AudioStream result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public chatserver.gen.AudioStream buildPartial() {
      chatserver.gen.AudioStream result = new chatserver.gen.AudioStream(this);
      result.roomId_ = roomId_;
      result.audio_ = audio_;
      result.audioFormat_ = audioFormat_;
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
      if (other instanceof chatserver.gen.AudioStream) {
        return mergeFrom((chatserver.gen.AudioStream)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(chatserver.gen.AudioStream other) {
      if (other == chatserver.gen.AudioStream.getDefaultInstance()) return this;
      if (other.getRoomId() != 0L) {
        setRoomId(other.getRoomId());
      }
      if (other.getAudio() != com.google.protobuf.ByteString.EMPTY) {
        setAudio(other.getAudio());
      }
      if (!other.getAudioFormat().isEmpty()) {
        audioFormat_ = other.audioFormat_;
        onChanged();
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
      chatserver.gen.AudioStream parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (chatserver.gen.AudioStream) e.getUnfinishedMessage();
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
     * <pre>
     * 传一下roomId吧，可能生成audioUrl会用到。
     * </pre>
     *
     * <code>int64 roomId = 1;</code>
     * @return The roomId.
     */
    @java.lang.Override
    public long getRoomId() {
      return roomId_;
    }
    /**
     * <pre>
     * 传一下roomId吧，可能生成audioUrl会用到。
     * </pre>
     *
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
     * <pre>
     * 传一下roomId吧，可能生成audioUrl会用到。
     * </pre>
     *
     * <code>int64 roomId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRoomId() {
      
      roomId_ = 0L;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString audio_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * 麦克风收录产生
     * </pre>
     *
     * <code>bytes audio = 4;</code>
     * @return The audio.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getAudio() {
      return audio_;
    }
    /**
     * <pre>
     * 麦克风收录产生
     * </pre>
     *
     * <code>bytes audio = 4;</code>
     * @param value The audio to set.
     * @return This builder for chaining.
     */
    public Builder setAudio(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      audio_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 麦克风收录产生
     * </pre>
     *
     * <code>bytes audio = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearAudio() {
      
      audio_ = getDefaultInstance().getAudio();
      onChanged();
      return this;
    }

    private java.lang.Object audioFormat_ = "";
    /**
     * <pre>
     * 音频传输格式, 支持列表见ReadMe
     * </pre>
     *
     * <code>string audioFormat = 5;</code>
     * @return The audioFormat.
     */
    public java.lang.String getAudioFormat() {
      java.lang.Object ref = audioFormat_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        audioFormat_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * 音频传输格式, 支持列表见ReadMe
     * </pre>
     *
     * <code>string audioFormat = 5;</code>
     * @return The bytes for audioFormat.
     */
    public com.google.protobuf.ByteString
        getAudioFormatBytes() {
      java.lang.Object ref = audioFormat_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        audioFormat_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * 音频传输格式, 支持列表见ReadMe
     * </pre>
     *
     * <code>string audioFormat = 5;</code>
     * @param value The audioFormat to set.
     * @return This builder for chaining.
     */
    public Builder setAudioFormat(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      audioFormat_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 音频传输格式, 支持列表见ReadMe
     * </pre>
     *
     * <code>string audioFormat = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearAudioFormat() {
      
      audioFormat_ = getDefaultInstance().getAudioFormat();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 音频传输格式, 支持列表见ReadMe
     * </pre>
     *
     * <code>string audioFormat = 5;</code>
     * @param value The bytes for audioFormat to set.
     * @return This builder for chaining.
     */
    public Builder setAudioFormatBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      audioFormat_ = value;
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


    // @@protoc_insertion_point(builder_scope:AudioStream)
  }

  // @@protoc_insertion_point(class_scope:AudioStream)
  private static final chatserver.gen.AudioStream DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new chatserver.gen.AudioStream();
  }

  public static chatserver.gen.AudioStream getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AudioStream>
      PARSER = new com.google.protobuf.AbstractParser<AudioStream>() {
    @java.lang.Override
    public AudioStream parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AudioStream(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AudioStream> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AudioStream> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public chatserver.gen.AudioStream getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

