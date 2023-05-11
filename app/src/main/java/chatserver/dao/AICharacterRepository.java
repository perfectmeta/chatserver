package chatserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface AICharacterRepository extends JpaRepository<AICharacter, Long> {
    AICharacter findCharacterByCharacterId(long characterId);
}
