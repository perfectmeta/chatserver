// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface GMCommandOrBuilder extends
    // @@protoc_insertion_point(interface_extends:GMCommand)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string command = 1;</code>
   * @return The command.
   */
  java.lang.String getCommand();
  /**
   * <code>string command = 1;</code>
   * @return The bytes for command.
   */
  com.google.protobuf.ByteString
      getCommandBytes();

  /**
   * <code>repeated string parameters = 2;</code>
   * @return A list containing the parameters.
   */
  java.util.List<java.lang.String>
      getParametersList();
  /**
   * <code>repeated string parameters = 2;</code>
   * @return The count of parameters.
   */
  int getParametersCount();
  /**
   * <code>repeated string parameters = 2;</code>
   * @param index The index of the element to return.
   * @return The parameters at the given index.
   */
  java.lang.String getParameters(int index);
  /**
   * <code>repeated string parameters = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the parameters at the given index.
   */
  com.google.protobuf.ByteString
      getParametersBytes(int index);
}