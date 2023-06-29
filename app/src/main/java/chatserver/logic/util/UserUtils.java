package chatserver.logic.util;

import chatserver.entity.User;
import chatserver.gen.Contact;
import com.google.common.base.Strings;

public class UserUtils {

    public static Contact parseContactByDbUser(User user) {
        var builder = Contact.newBuilder();
        builder.setNickName(user.getNickName());
        builder.setGender(user.getGender());
        builder.setHeadIconUrl(Strings.nullToEmpty(user.getHeadIconUrl()));

        builder.setEnglishName(Strings.nullToEmpty(user.getEnglishName()));
        builder.setLocation(Strings.nullToEmpty(user.getLocation()));
        builder.setPersonalizedSignature(Strings.nullToEmpty(user.getPersonalizedSignature()));
        builder.setBirthDay(Strings.nullToEmpty(user.getBirthDay()));
        builder.setInterest(Strings.nullToEmpty(user.getInterest()));
        builder.setCharacter(Strings.nullToEmpty(user.getDisposition()));
        builder.setFavoriteFoods(Strings.nullToEmpty(user.getFavoriteFoods()));
        builder.setUnInterest(Strings.nullToEmpty(user.getUnInterest()));
        builder.setLifeGoal(Strings.nullToEmpty(user.getLifeGoal()));
        return builder.build();
    }
}
