// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface CandidateRecommendOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CandidateRecommend)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 messageId = 1;</code>
   * @return The messageId.
   */
  long getMessageId();

  /**
   * <code>repeated string recommend = 2;</code>
   * @return A list containing the recommend.
   */
  java.util.List<java.lang.String>
      getRecommendList();
  /**
   * <code>repeated string recommend = 2;</code>
   * @return The count of recommend.
   */
  int getRecommendCount();
  /**
   * <code>repeated string recommend = 2;</code>
   * @param index The index of the element to return.
   * @return The recommend at the given index.
   */
  java.lang.String getRecommend(int index);
  /**
   * <code>repeated string recommend = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the recommend at the given index.
   */
  com.google.protobuf.ByteString
      getRecommendBytes(int index);
}
