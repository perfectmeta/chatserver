package chatserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface BotClassRepository extends JpaRepository<BotClass, Long> {
    BotClass findBotClassByBotClassId(long botClassId);
}
