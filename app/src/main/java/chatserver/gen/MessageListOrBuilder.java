// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface MessageListOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MessageList)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  java.util.List<chatserver.gen.Message> 
      getMessageListList();
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  chatserver.gen.Message getMessageList(int index);
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  int getMessageListCount();
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  java.util.List<? extends chatserver.gen.MessageOrBuilder> 
      getMessageListOrBuilderList();
  /**
   * <code>repeated .Message messageList = 1;</code>
   */
  chatserver.gen.MessageOrBuilder getMessageListOrBuilder(
      int index);
}
