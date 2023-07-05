// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface ChatResponseStreamOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ChatResponseStream)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 返回一个带messageId的消息，替代原来的临时message，这个对应关系客户端自己来维护
   * message 至少有3个状态，正在asr生成，正在发送，服务器确认
   * 本协议里两个Message结构会包含seq字段，供客户端用以确定对应发送的时哪条
   * </pre>
   *
   * <code>.Message requestMessage = 1;</code>
   * @return Whether the requestMessage field is set.
   */
  boolean hasRequestMessage();
  /**
   * <pre>
   * 返回一个带messageId的消息，替代原来的临时message，这个对应关系客户端自己来维护
   * message 至少有3个状态，正在asr生成，正在发送，服务器确认
   * 本协议里两个Message结构会包含seq字段，供客户端用以确定对应发送的时哪条
   * </pre>
   *
   * <code>.Message requestMessage = 1;</code>
   * @return The requestMessage.
   */
  chatserver.gen.Message getRequestMessage();
  /**
   * <pre>
   * 返回一个带messageId的消息，替代原来的临时message，这个对应关系客户端自己来维护
   * message 至少有3个状态，正在asr生成，正在发送，服务器确认
   * 本协议里两个Message结构会包含seq字段，供客户端用以确定对应发送的时哪条
   * </pre>
   *
   * <code>.Message requestMessage = 1;</code>
   */
  chatserver.gen.MessageOrBuilder getRequestMessageOrBuilder();

  /**
   * <pre>
   * chatgpt生成
   * </pre>
   *
   * <code>string text = 2;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <pre>
   * chatgpt生成
   * </pre>
   *
   * <code>string text = 2;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <pre>
   * tts生成
   * </pre>
   *
   * <code>bytes audio = 3;</code>
   * @return The audio.
   */
  com.google.protobuf.ByteString getAudio();

  /**
   * <pre>
   * 最后chatgpt回应的消息，汇总到这，有messageId
   * </pre>
   *
   * <code>.Message responseMessage = 4;</code>
   * @return Whether the responseMessage field is set.
   */
  boolean hasResponseMessage();
  /**
   * <pre>
   * 最后chatgpt回应的消息，汇总到这，有messageId
   * </pre>
   *
   * <code>.Message responseMessage = 4;</code>
   * @return The responseMessage.
   */
  chatserver.gen.Message getResponseMessage();
  /**
   * <pre>
   * 最后chatgpt回应的消息，汇总到这，有messageId
   * </pre>
   *
   * <code>.Message responseMessage = 4;</code>
   */
  chatserver.gen.MessageOrBuilder getResponseMessageOrBuilder();

  /**
   * <pre>
   * 发完正文后，可选可能会有这个字段，提示玩家后续可能发什么，也可能没有
   * </pre>
   *
   * <code>.CandidateRecommend candidates = 5;</code>
   * @return Whether the candidates field is set.
   */
  boolean hasCandidates();
  /**
   * <pre>
   * 发完正文后，可选可能会有这个字段，提示玩家后续可能发什么，也可能没有
   * </pre>
   *
   * <code>.CandidateRecommend candidates = 5;</code>
   * @return The candidates.
   */
  chatserver.gen.CandidateRecommend getCandidates();
  /**
   * <pre>
   * 发完正文后，可选可能会有这个字段，提示玩家后续可能发什么，也可能没有
   * </pre>
   *
   * <code>.CandidateRecommend candidates = 5;</code>
   */
  chatserver.gen.CandidateRecommendOrBuilder getCandidatesOrBuilder();

  public chatserver.gen.ChatResponseStream.ResponseCase getResponseCase();
}
