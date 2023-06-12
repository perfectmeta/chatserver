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
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RegisterInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RegisterInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RegisterFeedback_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RegisterFeedback_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RoomId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RoomId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_GetMemoryRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_GetMemoryRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Memory_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Memory_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DeleteMemoryRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DeleteMemoryRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DeleteMemoryResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DeleteMemoryResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Contact_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Contact_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CreateMineNPCsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CreateMineNPCsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CreateMineNPCsResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CreateMineNPCsResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_EstablishContactWithRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EstablishContactWithRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_EstablishContactWithResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EstablishContactWithResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DeleteContactRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DeleteContactRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_DeleteContactResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DeleteContactResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nchat.proto\"A\n\013AudioStream\022\016\n\006roomId\030\001 " +
      "\001(\003\022\r\n\005audio\030\004 \001(\014\022\023\n\013audioFormat\030\005 \001(\t\"" +
      "N\n\nTextStream\022\016\n\004text\030\001 \001(\tH\000\022\022\n\010audioUr" +
      "l\030\002 \001(\tH\000\022\020\n\006delete\030\004 \001(\005H\000B\n\n\010response\"" +
      "\211\001\n\013ChatRequest\022\016\n\006roomId\030\001 \001(\003\022\031\n\007msgTy" +
      "pe\030\005 \001(\0162\010.MsgType\022\014\n\004text\030\006 \001(\t\022\020\n\010audi" +
      "oUrl\030\007 \001(\t\022\020\n\010imageUrl\030\010 \001(\t\022\020\n\010videoUrl" +
      "\030\t \001(\t\022\013\n\003seq\030\n \001(\t\"\212\001\n\022ChatResponseStre" +
      "am\022\"\n\016requestMessage\030\001 \001(\0132\010.MessageH\000\022\016" +
      "\n\004text\030\002 \001(\tH\000\022\017\n\005audio\030\003 \001(\014H\000\022#\n\017respo" +
      "nseMessage\030\004 \001(\0132\010.MessageH\000B\n\n\010response" +
      "\"\306\001\n\007Message\022\016\n\006roomId\030\001 \001(\003\022\027\n\006author\030\003" +
      " \001(\0132\007.Author\022\023\n\013createdTime\030\004 \001(\003\022\031\n\007ms" +
      "gType\030\005 \001(\0162\010.MsgType\022\014\n\004text\030\006 \001(\t\022\020\n\010a" +
      "udioUrl\030\007 \001(\t\022\020\n\010imageUrl\030\010 \001(\t\022\020\n\010video" +
      "Url\030\t \001(\t\022\013\n\003seq\030\n \001(\t\022\021\n\tmessageId\030\013 \001(" +
      "\003\"4\n\006Author\022\014\n\004type\030\001 \001(\005\022\016\n\006userId\030\002 \001(" +
      "\003\022\014\n\004name\030\003 \001(\t\"\233\001\n\010RoomInfo\022\016\n\006roomId\030\001" +
      " \001(\003\022\020\n\010roomName\030\002 \001(\t\022\024\n\003you\030\003 \001(\0132\007.Au" +
      "thor\022\023\n\002ai\030\004 \001(\0132\007.Author\022\023\n\013createdTime" +
      "\030\005 \001(\003\022\026\n\016firstMessageId\030\006 \001(\003\022\025\n\rlastMe" +
      "ssageId\030\007 \001(\003\"\027\n\005Hello\022\016\n\006whoami\030\001 \001(\t\"9" +
      "\n\020EnterRoomRequest\022\016\n\006roomId\030\001 \001(\003\022\025\n\rla" +
      "stMessageId\030\002 \001(\003\">\n\014RegisterInfo\022\r\n\005ema" +
      "il\030\001 \001(\t\022\020\n\010nickname\030\002 \001(\t\022\r\n\005phone\030\003 \001(" +
      "\t\"\352\001\n\020RegisterFeedback\022\022\n\nstatusCode\030\001 \001" +
      "(\005\022\017\n\007message\030\002 \001(\t\022\016\n\006userId\030\003 \001(\005\"\240\001\n\n" +
      "StatusCode\022\006\n\002OK\020\000\022\024\n\020NICKNAME_INVALID\020\001" +
      "\022\025\n\021NICKNAME_CONFLICT\020\002\022\021\n\rPHONE_INVALID" +
      "\020\003\022\022\n\016PHONE_CONFLICT\020\004\022\021\n\rEMAIL_INVALID\020" +
      "\005\022\022\n\016EMAIL_CONFLICT\020\006\022\017\n\013OTHER_ERROR\020\007\"\030" +
      "\n\006RoomId\022\016\n\006roomId\030\001 \001(\005\"7\n\020GetMemoryReq" +
      "uest\022\016\n\006userId\030\001 \001(\003\022\023\n\013otherUserId\030\002 \001(" +
      "\003\"b\n\006Memory\022\020\n\010memoryId\030\001 \001(\003\022\014\n\004memo\030\002 " +
      "\001(\t\022\023\n\013createdTime\030\003 \001(\003\022\016\n\006userId\030\004 \001(\003" +
      "\022\023\n\013otherUserId\030\005 \001(\003\"L\n\023DeleteMemoryReq" +
      "uest\022\016\n\006userId\030\001 \001(\003\022\023\n\013otherUserId\030\002 \001(" +
      "\003\022\020\n\010memoryId\030\003 \001(\003\")\n\024DeleteMemoryRespo" +
      "nse\022\021\n\terrorCode\030\001 \001(\005\"\213\001\n\007Contact\022\020\n\010us" +
      "erType\030\001 \001(\005\022\020\n\010nickName\030\002 \001(\t\022\024\n\014catego" +
      "ryName\030\003 \001(\t\022\016\n\006gender\030\004 \001(\005\022\014\n\004tags\030\005 \001" +
      "(\t\022\023\n\013description\030\006 \001(\t\022\023\n\013headIconUrl\030\007" +
      " \001(\t\")\n\025CreateMineNPCsRequest\022\020\n\010categor" +
      "y\030\001 \001(\003\"(\n\026CreateMineNPCsResponse\022\016\n\006use" +
      "rId\030\001 \001(\003\"-\n\033EstablishContactWithRequest" +
      "\022\016\n\006userId\030\001 \001(\003\"1\n\034EstablishContactWith" +
      "Response\022\021\n\tcontactId\030\001 \001(\003\"&\n\024DeleteCon" +
      "tactRequest\022\016\n\006userId\030\001 \001(\003\"(\n\025DeleteCon" +
      "tactResponse\022\017\n\007errCode\030\001 \001(\003*\036\n\010UserTyp" +
      "e\022\t\n\005HUMAN\020\000\022\007\n\003BOT\020\001*S\n\007MsgType\022\023\n\017AUDI" +
      "O_WITH_TEXT\020\000\022\023\n\017TEXT_WITH_AUDIO\020\001\022\010\n\004TE" +
      "XT\020\002\022\t\n\005IMAGE\020\003\022\t\n\005VIDEO\020\0042\306\003\n\013ChatServi" +
      "ce\022*\n\006Signup\022\r.RegisterInfo\032\021.RegisterFe" +
      "edback\022\037\n\013GetSelfInfo\022\006.Hello\032\010.Contact\022" +
      "$\n\rGetRoomStream\022\006.Hello\032\t.RoomInfo0\001\022&\n" +
      "\020GetContactStream\022\006.Hello\032\010.Contact0\001\022)\n" +
      "\023GetNewMessageStream\022\006.Hello\032\010.Message0\001" +
      "\022*\n\tEnterRoom\022\021.EnterRoomRequest\032\010.Messa" +
      "ge0\001\0220\n\017SpeechRecognize\022\014.AudioStream\032\013." +
      "TextStream(\0010\001\022+\n\004Chat\022\014.ChatRequest\032\023.C" +
      "hatResponseStream0\001\022)\n\tGetMemory\022\021.GetMe" +
      "moryRequest\032\007.Memory0\001\022;\n\014DeleteMemory\022\024" +
      ".DeleteMemoryRequest\032\025.DeleteMemoryRespo" +
      "nseB\035\n\016chatserver.genP\001\252\002\010Chat.Genb\006prot" +
      "o3"
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
        new java.lang.String[] { "RoomId", "Audio", "AudioFormat", });
    internal_static_TextStream_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_TextStream_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TextStream_descriptor,
        new java.lang.String[] { "Text", "AudioUrl", "Delete", "Response", });
    internal_static_ChatRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ChatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ChatRequest_descriptor,
        new java.lang.String[] { "RoomId", "MsgType", "Text", "AudioUrl", "ImageUrl", "VideoUrl", "Seq", });
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
        new java.lang.String[] { "RoomId", "Author", "CreatedTime", "MsgType", "Text", "AudioUrl", "ImageUrl", "VideoUrl", "Seq", "MessageId", });
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
        new java.lang.String[] { "Whoami", });
    internal_static_EnterRoomRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_EnterRoomRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_EnterRoomRequest_descriptor,
        new java.lang.String[] { "RoomId", "LastMessageId", });
    internal_static_RegisterInfo_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_RegisterInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RegisterInfo_descriptor,
        new java.lang.String[] { "Email", "Nickname", "Phone", });
    internal_static_RegisterFeedback_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_RegisterFeedback_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RegisterFeedback_descriptor,
        new java.lang.String[] { "StatusCode", "Message", "UserId", });
    internal_static_RoomId_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_RoomId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RoomId_descriptor,
        new java.lang.String[] { "RoomId", });
    internal_static_GetMemoryRequest_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_GetMemoryRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GetMemoryRequest_descriptor,
        new java.lang.String[] { "UserId", "OtherUserId", });
    internal_static_Memory_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_Memory_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Memory_descriptor,
        new java.lang.String[] { "MemoryId", "Memo", "CreatedTime", "UserId", "OtherUserId", });
    internal_static_DeleteMemoryRequest_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_DeleteMemoryRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DeleteMemoryRequest_descriptor,
        new java.lang.String[] { "UserId", "OtherUserId", "MemoryId", });
    internal_static_DeleteMemoryResponse_descriptor =
      getDescriptor().getMessageTypes().get(15);
    internal_static_DeleteMemoryResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DeleteMemoryResponse_descriptor,
        new java.lang.String[] { "ErrorCode", });
    internal_static_Contact_descriptor =
      getDescriptor().getMessageTypes().get(16);
    internal_static_Contact_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Contact_descriptor,
        new java.lang.String[] { "UserType", "NickName", "CategoryName", "Gender", "Tags", "Description", "HeadIconUrl", });
    internal_static_CreateMineNPCsRequest_descriptor =
      getDescriptor().getMessageTypes().get(17);
    internal_static_CreateMineNPCsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CreateMineNPCsRequest_descriptor,
        new java.lang.String[] { "Category", });
    internal_static_CreateMineNPCsResponse_descriptor =
      getDescriptor().getMessageTypes().get(18);
    internal_static_CreateMineNPCsResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CreateMineNPCsResponse_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_EstablishContactWithRequest_descriptor =
      getDescriptor().getMessageTypes().get(19);
    internal_static_EstablishContactWithRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_EstablishContactWithRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_EstablishContactWithResponse_descriptor =
      getDescriptor().getMessageTypes().get(20);
    internal_static_EstablishContactWithResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_EstablishContactWithResponse_descriptor,
        new java.lang.String[] { "ContactId", });
    internal_static_DeleteContactRequest_descriptor =
      getDescriptor().getMessageTypes().get(21);
    internal_static_DeleteContactRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DeleteContactRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_DeleteContactResponse_descriptor =
      getDescriptor().getMessageTypes().get(22);
    internal_static_DeleteContactResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_DeleteContactResponse_descriptor,
        new java.lang.String[] { "ErrCode", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
