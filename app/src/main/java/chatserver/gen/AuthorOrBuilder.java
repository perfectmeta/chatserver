// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface AuthorOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Author)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 0: 真人，1：bot， 之后改为UserType
   * </pre>
   *
   * <code>int32 type = 1;</code>
   * @return The type.
   */
  int getType();

  /**
   * <code>int64 userId = 2;</code>
   * @return The userId.
   */
  long getUserId();

  /**
   * <code>string name = 3;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 3;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();
}