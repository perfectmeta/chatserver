package chatserver.util;

import chatserver.config.ConfigManager;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

public class FileWatcherTest {

    @Test
    public void directoryWatcherTest() throws InterruptedException {

        var rootPath = Paths.get("../configdir");
        FileWatcher watcher = new FileWatcher(rootPath.resolve("changelist.txt"));
        var latch = new CountDownLatch(1);
        watcher.watch(() -> {
            ConfigManager.reload(rootPath);
            latch.countDown();
        });

        latch.await();
    }
}
