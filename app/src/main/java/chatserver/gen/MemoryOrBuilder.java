// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface MemoryOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Memory)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 memoryId = 1;</code>
   * @return The memoryId.
   */
  long getMemoryId();

  /**
   * <code>string memo = 2;</code>
   * @return The memo.
   */
  java.lang.String getMemo();
  /**
   * <code>string memo = 2;</code>
   * @return The bytes for memo.
   */
  com.google.protobuf.ByteString
      getMemoBytes();

  /**
   * <code>int64 createdTime = 3;</code>
   * @return The createdTime.
   */
  long getCreatedTime();

  /**
   * <code>int64 userId = 4;</code>
   * @return The userId.
   */
  long getUserId();

  /**
   * <code>int64 otherUserId = 5;</code>
   * @return The otherUserId.
   */
  long getOtherUserId();
}
