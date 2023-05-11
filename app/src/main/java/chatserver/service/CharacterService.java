package chatserver.service;

import chatserver.dao.AICharacter;
import chatserver.dao.AICharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final AICharacterRepository aiCharacters;

    public CharacterService(AICharacterRepository aiCharacters) {
        this.aiCharacters = aiCharacters;
    }

    public List<AICharacter> getAllAICharacters() {
        return aiCharacters.findAll();
    }
}
