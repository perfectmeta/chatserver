package chatserver.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class FileWatcher {
    private static final Logger logger = Logger.getLogger(FileWatcher.class.getName());

    private final Path watchPath;
    private volatile Runnable handler;
    private Thread workerThread;


    public FileWatcher(Path filePath) {
        watchPath = filePath;

    }

    public void watch(Runnable handler) {
        if (this.handler != null) {
            logger.warning("already watched, ignore this watch!");
            return;
        }

        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            watchPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            logger.warning("watch exception, " + e.getMessage());
            return;
        }

        workerThread = Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    watchService.take();
                    Runnable handle = this.handler;
                    if (handle != null) {
                        handle.run();
                    }
                } catch (InterruptedException e) {
                    if (Thread.interrupted()) {
                        break;
                    }
                }
            }
        });

        this.handler = handler;
    }

    public void stop() {
        this.handler = null;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

}
