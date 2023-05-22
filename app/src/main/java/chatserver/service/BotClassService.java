package chatserver.service;

import chatserver.dao.BotClass;
import chatserver.dao.BotClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotClassService {

    private final BotClassRepository botClasses;

    public BotClassService(BotClassRepository botClasses) {
        this.botClasses = botClasses;
    }

    public List<BotClass> getAllBotClasses() {
        return botClasses.findAll();
    }

    public BotClass findBotClassById(long id) {
        return botClasses.findBotClassByBotClassId(id);
    }
}
