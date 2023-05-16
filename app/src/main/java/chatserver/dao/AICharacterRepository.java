package chatserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface AICharacterRepository extends JpaRepository<AICharacter, Long> {
    AICharacter findCharacterByCharacterId(long characterId);

    @Query("SELECT a FROM AICharacter a JOIN Room b ON a.characterId = b.aiUserId " +
            "where b.roomId = :roomId")
    AICharacter findAICharacterByRoom_RoomId(@Param("roomId") long roomId);
}
