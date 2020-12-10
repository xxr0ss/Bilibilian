package WebCrawler;

import DB.DBManager;
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

    public TaskRunner(Task task) {
        this.task = task;
    }

    public String getTaskName() {
        return task.getClass().getSimpleName();
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

    public void wantToPause(boolean pause) {
        if (pause) {
            status = StatusConstants.PAUSED;
        } else {
            status = StatusConstants.RUNNING;
        }
    }

    public void wantToTerminate() {
        status = StatusConstants.TERMINATED;
    }


    @Override
    public void run() {
//        System.out.println(task.getClass().getName() + " Started"); // DEBUG
        status = StatusConstants.RUNNING;
        super.run();
        DBManager dbManager = DBManager.getDBManager();
        dbManager.setupDB();
        while (true) {
            task.crawl();
            try {
                // 爬虫爬取间隔时间，爬太快太多数据会被ban
                Thread.sleep(5000);
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

            if (task.isFinished()) {
                break;
            }
            // get valid result from api, now need to save to database
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
}

class StatusConstants {
    // some constants refer to taskRunner status
    public final static int NOT_IMPLEMENTED = 0; // reserved

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
