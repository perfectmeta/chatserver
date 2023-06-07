// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface TextStreamOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TextStream)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * asr生成
   * </pre>
   *
   * <code>string text = 1;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <pre>
   * asr生成
   * </pre>
   *
   * <code>string text = 1;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <pre>
   * 当audio stream结束被存下来的时候发
   * </pre>
   *
   * <code>string audioUrl = 2;</code>
   * @return The audioUrl.
   */
  java.lang.String getAudioUrl();
  /**
   * <pre>
   * 当audio stream结束被存下来的时候发
   * </pre>
   *
   * <code>string audioUrl = 2;</code>
   * @return The bytes for audioUrl.
   */
  com.google.protobuf.ByteString
      getAudioUrlBytes();

  /**
   * <pre>
   * asr生成, 删除前面多少个字符
   * </pre>
   *
   * <code>int32 delete = 4;</code>
   * @return The delete.
   */
  int getDelete();

  public chatserver.gen.TextStream.ResponseCase getResponseCase();
}
