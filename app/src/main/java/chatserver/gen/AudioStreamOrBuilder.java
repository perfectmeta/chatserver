// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface AudioStreamOrBuilder extends
    // @@protoc_insertion_point(interface_extends:AudioStream)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 roomId = 1;</code>
   * @return The roomId.
   */
  long getRoomId();

  /**
   * <pre>
   * 麦克风收录产生
   * </pre>
   *
   * <code>bytes audio = 4;</code>
   * @return The audio.
   */
  com.google.protobuf.ByteString getAudio();
}
