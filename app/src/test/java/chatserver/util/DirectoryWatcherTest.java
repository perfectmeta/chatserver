package chatserver.util;

import org.junit.jupiter.api.Test;

public class DirectoryWatcherTest {

    @Test
    public void directoryWatcherTest() {
        DirectoryWatcher watcher = new DirectoryWatcher("C:\\Users\\kip\\Documents\\workspace\\chatserver");
        watcher.watch((a, b) -> {});
        while(true) {
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
