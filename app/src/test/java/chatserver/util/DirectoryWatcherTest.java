package chatserver.util;

import chatserver.config.Config;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class DirectoryWatcherTest {
    private static final Logger logger = Logger.getLogger(DirectoryWatcherTest.class.getName());

    @Test
    public void directoryWatcherTest() throws InterruptedException {
        Config config = Config.getInstance();
        var rootPath = Paths.get("C:\\Users\\kip\\Documents\\workspace\\chatserver\\configdir");
        DirectoryWatcher watcher = new DirectoryWatcher(rootPath.toString(), "changelist.txt");
        var latch = new CountDownLatch(1);
        watcher.watch((a, b) -> {
            config.reload(rootPath, a, b);
            logger.info("reloading " + a + " " + b.toString());
            latch.countDown();
        });

        latch.await();
    }
}
