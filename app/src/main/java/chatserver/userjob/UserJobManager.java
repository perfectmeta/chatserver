package chatserver.userjob;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserJobManager {
    private static class UserJobManagerHolder {
        public static UserJobManager INSTANCE = new UserJobManager();
    }

    private final LinkedBlockingQueue<UserJobBase> userJobs;
    private final ScheduledExecutorService executorService;

    public static UserJobManager getInstance() {
        return UserJobManagerHolder.INSTANCE;
    }

    private UserJobManager() {
        userJobs = new LinkedBlockingQueue<>();
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            while (!userJobs.isEmpty()) {
                var userJob = userJobs.poll();
                try {
                    userJob.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public void commitJob(UserJobBase job) {
        userJobs.add(job);
    }
}
