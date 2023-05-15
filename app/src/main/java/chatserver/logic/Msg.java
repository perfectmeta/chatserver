package chatserver.logic;

import chatserver.gen.Author;
import chatserver.gen.Message;
import chatserver.gen.MsgType;

import java.util.Objects;

public class Msg {
    public static final int UT_HUMAN = 0;  //user type
    public static final int UT_AI_TEACHER = 1;
    public static final int UT_AI_FRIEND = 2;


    public static Message fromDb(chatserver.dao.Message dm) {
        Message.Builder b = Message.newBuilder();
        b.setRoomId(dm.getRoomId());
        b.setAuthor(Author.newBuilder().setType(dm.getAuthorUserType())
                            .setUserId(dm.getAuthorUserId())
                            .setName(dm.getAuthorShowName()));
        b.setCreatedTime(dm.getCreatedTime());

        b.setMsgType(MsgType.forNumber(dm.getMsgType()));
        b.setText(dm.getText());
        b.setAudioUrl(Objects.isNull(dm.getAudioUrl()) ? "" : dm.getAudioUrl());
        b.setImageUrl(Objects.isNull(dm.getImageUrl()) ? "" : dm.getImageUrl());
        b.setVideoUrl(Objects.isNull(dm.getVideoUrl()) ? "" : dm.getVideoUrl());
        b.setMessageId(dm.getMessageId());

        return b.build();
    }
}
