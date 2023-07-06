package chatserver.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Logger;

public class FileWatcher {
    private static final Logger logger = Logger.getLogger(FileWatcher.class.getName());

    private final Path watchPath;
    private final Path watchFile;
    private volatile Runnable handler;
    private Thread workerThread;


    public FileWatcher(Path filePath) {
        watchFile = filePath;
        watchPath = filePath.getParent().toAbsolutePath();
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
                    var key = watchService.take();
                    try {
                        for (var e : key.pollEvents()) {
                            var p = (Path)e.context();
                            logger.info("Change %s".formatted(p.toAbsolutePath().toString()));
                            if (p.toAbsolutePath().equals(watchFile)) {
                                Runnable handle = this.handler;
                                if (handle != null) {
                                    handle.run();
                                }
                                break;
                            }
                        }
                    } finally {
                        key.reset();
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
