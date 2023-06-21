package chatserver.util;

import chatserver.config.Config;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.logging.Logger;

public class DirectoryWatcherTest {
    private static final Logger logger = Logger.getLogger(DirectoryWatcherTest.class.getName());

    @Test
    public void directoryWatcherTest() {
        Config config = Config.getInstance();
        var rootPath = Paths.get("C:\\Users\\kip\\Documents\\workspace\\chatserver\\configdir");
        DirectoryWatcher watcher = new DirectoryWatcher(rootPath.toString());
        watcher.watch((a, b) -> {
            config.reload(rootPath, a, b);
            logger.info("reloading " + a.toString() + " " + b.toString());
        });

        while(true) {
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // config.rebots();
    }
}
