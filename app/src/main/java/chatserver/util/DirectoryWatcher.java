package chatserver.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class DirectoryWatcher {
    private static final Logger logger = Logger.getLogger(DirectoryWatcher.class.getName());

    // private final String watchPath;
    private final Path watchPath;
    private Thread workerThread;
    private String flagFileName;

    private BiConsumer<Path, WatchEvent.Kind<Path>> handler;
    public DirectoryWatcher(String path, String flagFileName) {
        this.watchPath = Paths.get(path);
        this.flagFileName = flagFileName;
    }

    public void watch(BiConsumer<Path, WatchEvent.Kind<Path>> handler) {
        this.handler = handler;
        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            registerRecursive(watchPath, watchService);
            watchPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        workerThread = Thread.startVirtualThread(()->{
            while (true) {
                WatchKey key = null;
                try {
                    key = watchService.take();
                    Objects.requireNonNull(key);
                    for (var event : key.pollEvents()) {
                        //noinspection unchecked
                        WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                        var context = pathEvent.context();
                        var filePath = watchPath.resolve(context);
                        var fileName = filePath.getFileName().toString();
                        logger.info(filePath.toAbsolutePath() + " event " + event.kind().name());

                        if (fileName.equals(flagFileName)) {
                            this.handler.accept(pathEvent.context(), pathEvent.kind());
                        }
                    }
                } catch (InterruptedException e) {
                    if (Thread.interrupted()) {
                        break;
                    }
                } finally {
                    if (key != null) {
                        key.reset();
                    }
                }
            }
        });
    }

    public void stop() {
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    private void registerRecursive(Path directoryOrFile, WatchService watchService) throws IOException {
        if (!Files.isDirectory(directoryOrFile)) {
            directoryOrFile.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            return;
        }
        Files.walkFileTree(directoryOrFile, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                try {
                    dir.register(watchService,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.ENTRY_DELETE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!Files.isDirectory(file))
                    return FileVisitResult.CONTINUE;
                try {
                    file.register(watchService,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.ENTRY_DELETE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
