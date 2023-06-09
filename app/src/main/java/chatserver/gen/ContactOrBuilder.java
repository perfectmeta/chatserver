// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chatserver.gen;

public interface ContactOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Contact)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 0: human , 1: bot
   * </pre>
   *
   * <code>int32 userType = 1;</code>
   * @return The userType.
   */
  int getUserType();

  /**
   * <code>string nickName = 2;</code>
   * @return The nickName.
   */
  java.lang.String getNickName();
  /**
   * <code>string nickName = 2;</code>
   * @return The bytes for nickName.
   */
  com.google.protobuf.ByteString
      getNickNameBytes();

  /**
   * <code>string categoryName = 3;</code>
   * @return The categoryName.
   */
  java.lang.String getCategoryName();
  /**
   * <code>string categoryName = 3;</code>
   * @return The bytes for categoryName.
   */
  com.google.protobuf.ByteString
      getCategoryNameBytes();

  /**
   * <pre>
   * 0: unknown, 1: male, 2: female
   * </pre>
   *
   * <code>int32 gender = 4;</code>
   * @return The gender.
   */
  int getGender();

  /**
   * <code>string tags = 5;</code>
   * @return The tags.
   */
  java.lang.String getTags();
  /**
   * <code>string tags = 5;</code>
   * @return The bytes for tags.
   */
  com.google.protobuf.ByteString
      getTagsBytes();

  /**
   * <code>string description = 6;</code>
   * @return The description.
   */
  java.lang.String getDescription();
  /**
   * <code>string description = 6;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();

  /**
   * <code>string headIconUrl = 7;</code>
   * @return The headIconUrl.
   */
  java.lang.String getHeadIconUrl();
  /**
   * <code>string headIconUrl = 7;</code>
   * @return The bytes for headIconUrl.
   */
  com.google.protobuf.ByteString
      getHeadIconUrlBytes();

  /**
   * <code>string englishName = 8;</code>
   * @return The englishName.
   */
  java.lang.String getEnglishName();
  /**
   * <code>string englishName = 8;</code>
   * @return The bytes for englishName.
   */
  com.google.protobuf.ByteString
      getEnglishNameBytes();

  /**
   * <code>string location = 9;</code>
   * @return The location.
   */
  java.lang.String getLocation();
  /**
   * <code>string location = 9;</code>
   * @return The bytes for location.
   */
  com.google.protobuf.ByteString
      getLocationBytes();

  /**
   * <code>string personalizedSignature = 10;</code>
   * @return The personalizedSignature.
   */
  java.lang.String getPersonalizedSignature();
  /**
   * <code>string personalizedSignature = 10;</code>
   * @return The bytes for personalizedSignature.
   */
  com.google.protobuf.ByteString
      getPersonalizedSignatureBytes();

  /**
   * <code>string birthDay = 11;</code>
   * @return The birthDay.
   */
  java.lang.String getBirthDay();
  /**
   * <code>string birthDay = 11;</code>
   * @return The bytes for birthDay.
   */
  com.google.protobuf.ByteString
      getBirthDayBytes();

  /**
   * <code>string interest = 12;</code>
   * @return The interest.
   */
  java.lang.String getInterest();
  /**
   * <code>string interest = 12;</code>
   * @return The bytes for interest.
   */
  com.google.protobuf.ByteString
      getInterestBytes();

  /**
   * <code>string character = 13;</code>
   * @return The character.
   */
  java.lang.String getCharacter();
  /**
   * <code>string character = 13;</code>
   * @return The bytes for character.
   */
  com.google.protobuf.ByteString
      getCharacterBytes();

  /**
   * <code>string favoriteFoods = 14;</code>
   * @return The favoriteFoods.
   */
  java.lang.String getFavoriteFoods();
  /**
   * <code>string favoriteFoods = 14;</code>
   * @return The bytes for favoriteFoods.
   */
  com.google.protobuf.ByteString
      getFavoriteFoodsBytes();

  /**
   * <code>string unInterest = 15;</code>
   * @return The unInterest.
   */
  java.lang.String getUnInterest();
  /**
   * <code>string unInterest = 15;</code>
   * @return The bytes for unInterest.
   */
  com.google.protobuf.ByteString
      getUnInterestBytes();

  /**
   * <code>string lifeGoal = 16;</code>
   * @return The lifeGoal.
   */
  java.lang.String getLifeGoal();
  /**
   * <code>string lifeGoal = 16;</code>
   * @return The bytes for lifeGoal.
   */
  com.google.protobuf.ByteString
      getLifeGoalBytes();

  /**
   * <code>string artistModel = 17;</code>
   * @return The artistModel.
   */
  java.lang.String getArtistModel();
  /**
   * <code>string artistModel = 17;</code>
   * @return The bytes for artistModel.
   */
  com.google.protobuf.ByteString
      getArtistModelBytes();
}
