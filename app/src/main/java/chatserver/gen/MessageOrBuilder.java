// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface MessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Message)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 roomId = 1;</code>
   * @return The roomId.
   */
  long getRoomId();

  /**
   * <code>.Author author = 3;</code>
   * @return Whether the author field is set.
   */
  boolean hasAuthor();
  /**
   * <code>.Author author = 3;</code>
   * @return The author.
   */
  chatserver.gen.Author getAuthor();
  /**
   * <code>.Author author = 3;</code>
   */
  chatserver.gen.AuthorOrBuilder getAuthorOrBuilder();

  /**
   * <code>int64 createdTime = 4;</code>
   * @return The createdTime.
   */
  long getCreatedTime();

  /**
   * <code>.MsgType msgType = 5;</code>
   * @return The enum numeric value on the wire for msgType.
   */
  int getMsgTypeValue();
  /**
   * <code>.MsgType msgType = 5;</code>
   * @return The msgType.
   */
  chatserver.gen.MsgType getMsgType();

  /**
   * <code>string text = 6;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <code>string text = 6;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <code>string audioUrl = 7;</code>
   * @return The audioUrl.
   */
  java.lang.String getAudioUrl();
  /**
   * <code>string audioUrl = 7;</code>
   * @return The bytes for audioUrl.
   */
  com.google.protobuf.ByteString
      getAudioUrlBytes();

  /**
   * <code>string imageUrl = 8;</code>
   * @return The imageUrl.
   */
  java.lang.String getImageUrl();
  /**
   * <code>string imageUrl = 8;</code>
   * @return The bytes for imageUrl.
   */
  com.google.protobuf.ByteString
      getImageUrlBytes();

  /**
   * <code>string videoUrl = 9;</code>
   * @return The videoUrl.
   */
  java.lang.String getVideoUrl();
  /**
   * <code>string videoUrl = 9;</code>
   * @return The bytes for videoUrl.
   */
  com.google.protobuf.ByteString
      getVideoUrlBytes();

  /**
   * <code>string seq = 10;</code>
   * @return The seq.
   */
  java.lang.String getSeq();
  /**
   * <code>string seq = 10;</code>
   * @return The bytes for seq.
   */
  com.google.protobuf.ByteString
      getSeqBytes();

  /**
   * <code>int64 messageId = 11;</code>
   * @return The messageId.
   */
  long getMessageId();
}
