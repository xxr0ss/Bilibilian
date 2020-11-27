package WebCrawler;

import WebCrawler.Tasks.Task;

/**
 * 作为一个线程来执行指定的爬取task
 * 负责对控制请求进行边界检查
 * <p>
 * <b>使用方法</b>
 * </br>
 * <code>
 * Task task = new SomeTask(initialization settings);
 * TaskRunner someTaskRunner = new TaskRunner(task);
 * </code>
 */
public class TaskRunner extends Thread {
    private final Object lock = new Object(); // 用于暂停时的锁

    private final Task task;

    public Task getTask() {
        return task;
    }

    public TaskRunner(Task task) {
        this.task = task;
    }

    // 既作为控制线程的标志，也作为返回线程状态的标志，默认状态为NOT_STARTED
    private int status = StatusConstants.NOT_STARTED;

    public String getStatusDescription() {
        return StatusConstants.constDescription[status];
    }


    // getter and setter
    public boolean isPaused() {
        return StatusConstants.PAUSED == status;
    }

    public void wantToPause() {
        status = StatusConstants.PAUSED;
    }

    public void wantToTerminate() {
        status = StatusConstants.TERMINATED;
    }


    @Override
    public void run() {
        status = StatusConstants.RUNNING;
        super.run();
        while (true) {
            task.crawl();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (StatusConstants.PAUSED == status) {
                // TODO: do something before pause
                while (StatusConstants.PAUSED == status) {
                    onPause();
                }
                // TODO: do something after recovery from pause
            }

            if (StatusConstants.TERMINATED == status) {
                break;
            }

            if (task.taskFinished()) {
                break;
            }
        }
        onComplete();
        status = StatusConstants.TERMINATED;
    }

    private void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onComplete() {
        System.out.println("task finished");
    }
    // TODO 获取爬取进度的函数
}

class StatusConstants {
    // some constants refer to taskRunner status
    public final static int NOT_IMPLEMENTED = 0;

    public final static int NOT_STARTED = 1;

    public final static int RUNNING = 2;

    public final static int PAUSED = 3;

    public final static int TERMINATED = 4;

    // 对上面的这些常数的描述
    public static String[] constDescription = {
            "not implemented",
            "not started",
            "running",
            "paused",
            "completed"
    };
}
