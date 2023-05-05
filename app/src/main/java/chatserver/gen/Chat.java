// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public final class Chat {
  private Chat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AudioStream_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AudioStream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TextStream_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TextStream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ChatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ChatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ChatResponseStream_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ChatResponseStream_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Message_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Message_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Author_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Author_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RoomInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RoomInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Hello_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Hello_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_EnterRoomRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EnterRoomRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nchat.proto\",\n\013AudioStream\022\016\n\006roomId\030\001 " +
      "\001(\003\022\r\n\005audio\030\004 \001(\014\"<\n\nTextStream\022\016\n\004text" +
      "\030\001 \001(\tH\000\022\022\n\010audioUrl\030\002 \001(\tH\000B\n\n\010response" +
      "\"|\n\013ChatRequest\022\016\n\006roomId\030\001 \001(\003\022\031\n\007msgTy" +
      "pe\030\005 \001(\0162\010.MsgType\022\014\n\004text\030\006 \001(\t\022\020\n\010audi" +
      "oUrl\030\007 \001(\t\022\020\n\010imageUrl\030\010 \001(\t\022\020\n\010videoUrl" +
      "\030\t \001(\t\"\212\001\n\022ChatResponseStream\022\"\n\016request" +
      "Message\030\001 \001(\0132\010.MessageH\000\022\016\n\004text\030\002 \001(\tH" +
      "\000\022\017\n\005audio\030\003 \001(\014H\000\022#\n\017responseMessage\030\004 " +
      "\001(\0132\010.MessageH\000B\n\n\010response\"\246\001\n\007Message\022" +
      "\016\n\006roomId\030\001 \001(\003\022\027\n\006author\030\003 \001(\0132\007.Author" +
      "\022\023\n\013createdTime\030\004 \001(\003\022\031\n\007msgType\030\005 \001(\0162\010" +
      ".MsgType\022\014\n\004text\030\006 \001(\t\022\020\n\010audioUrl\030\007 \001(\t" +
      "\022\020\n\010imageUrl\030\010 \001(\t\022\020\n\010videoUrl\030\t \001(\t\"4\n\006" +
      "Author\022\014\n\004type\030\001 \001(\005\022\016\n\006userId\030\002 \001(\003\022\014\n\004" +
      "name\030\003 \001(\t\"\233\001\n\010RoomInfo\022\016\n\006roomId\030\001 \001(\003\022" +
      "\020\n\010roomName\030\002 \001(\t\022\024\n\003you\030\003 \001(\0132\007.Author\022" +
      "\023\n\002ai\030\004 \001(\0132\007.Author\022\023\n\013createdTime\030\005 \001(" +
      "\003\022\026\n\016firstMessageId\030\006 \001(\003\022\025\n\rlastMessage" +
      "Id\030\007 \001(\003\"\007\n\005Hello\"9\n\020EnterRoomRequest\022\016\n" +
      "\006roomId\030\001 \001(\003\022\025\n\rlastMessageId\030\002 \001(\003*S\n\007" +
      "MsgType\022\023\n\017AUDIO_WITH_TEXT\020\000\022\023\n\017TEXT_WIT" +
      "H_AUDIO\020\001\022\010\n\004TEXT\020\002\022\t\n\005IMAGE\020\003\022\t\n\005VIDEO\020" +
      "\0042\347\001\n\013ChatService\022\"\n\013GetRoomList\022\006.Hello" +
      "\032\t.RoomInfo0\001\022)\n\023GetNewMessageStream\022\006.H" +
      "ello\032\010.Message0\001\022*\n\tEnterRoom\022\021.EnterRoo" +
      "mRequest\032\010.Message0\001\0220\n\017SpeechRecognize\022" +
      "\014.AudioStream\032\013.TextStream(\0010\001\022+\n\004Chat\022\014" +
      ".ChatRequest\032\023.ChatResponseStream0\001B\022\n\016c" +
      "hatserver.genP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_AudioStream_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AudioStream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AudioStream_descriptor,
        new java.lang.String[] { "RoomId", "Audio", });
    internal_static_TextStream_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_TextStream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TextStream_descriptor,
        new java.lang.String[] { "Text", "AudioUrl", "Response", });
    internal_static_ChatRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ChatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ChatRequest_descriptor,
        new java.lang.String[] { "RoomId", "MsgType", "Text", "AudioUrl", "ImageUrl", "VideoUrl", });
    internal_static_ChatResponseStream_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ChatResponseStream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ChatResponseStream_descriptor,
        new java.lang.String[] { "RequestMessage", "Text", "Audio", "ResponseMessage", "Response", });
    internal_static_Message_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Message_descriptor,
        new java.lang.String[] { "RoomId", "Author", "CreatedTime", "MsgType", "Text", "AudioUrl", "ImageUrl", "VideoUrl", });
    internal_static_Author_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_Author_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Author_descriptor,
        new java.lang.String[] { "Type", "UserId", "Name", });
    internal_static_RoomInfo_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_RoomInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RoomInfo_descriptor,
        new java.lang.String[] { "RoomId", "RoomName", "You", "Ai", "CreatedTime", "FirstMessageId", "LastMessageId", });
    internal_static_Hello_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_Hello_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Hello_descriptor,
        new java.lang.String[] { });
    internal_static_EnterRoomRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_EnterRoomRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_EnterRoomRequest_descriptor,
        new java.lang.String[] { "RoomId", "LastMessageId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
