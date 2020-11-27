package WebCrawler;

import WebCrawler.Tasks.Task;
import WebCrawler.Tasks.UserInfoTask;
import WebCrawler.Tasks.VideoInfoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 以单例模式实现的CrawlerManager，用于和UI交互
 * <p>
 * Usage:
 * CrawlerManager crawlerManager = CrawlerManager.getCrawlerManager();
 */
public class CrawlerManager {
    // CrawlerManager的单例模式实现
    private static CrawlerManager instance;

    private CrawlerManager() {
        initTaskList();
    }

    /**
     * 获取CrawlerManager实例
     *
     * @return CrawlerManager instance
     */
    public static CrawlerManager getCrawlerManager() {
        if (instance == null) {
            synchronized (CrawlerManager.class) {
                if (instance == null)
                    instance = new CrawlerManager();
            }
        }
        return instance;
    }


    private Task[] tasks;

    private void initTaskList() {
        tasks = new Task[]{
                new UserInfoTask(),
                new VideoInfoTask()
        };
    }

    /**
     * 获取WebCrawler.Tasks底下继承Task的类的名字
     * @return 任务名数组
     */
    public String[] getSupportedTasks() {
        List<String> tasksName = new ArrayList<>();
        for(Task t: tasks) {
            String classname = t.getClass().getName();
            String[] tmp = classname.split("\\.");
            String name = tmp[tmp.length - 1];
            tasksName.add(name.substring(0, name.length()-4)); // 去掉尾部类名的"Task"
        }
        return tasksName.toArray(new String[0]);
    }


    // TaskRunner Related

    private final List<TaskRunner> runnersList = new ArrayList<>();

    /**
     * 往Runner列表添加计划task
     * @param taskId 支持的task在getSupportedTasks() 数组的下标
     */
    public void addTaskRunner(int taskId) {
        TaskRunner runner = new TaskRunner(tasks[taskId]);
        runnersList.add(runner);
    }

    /**
     * 完成所有Runner的添加（和配置）后，一次性提交。
     */
    public void commitAllRunners() {
        for (TaskRunner runner: runnersList) {
            Thread t = new Thread(runner);
            t.start();
        }
    }

    public String[] getRunnersTaskStatus() {
        List<String> statusDescriptions = new ArrayList<>();
        for(TaskRunner r: runnersList) {
            statusDescriptions.add(r.getStatusDescription());
        }
        return statusDescriptions.toArray(new String[0]);
    }

    public String tempTestQueryProgress() {
        return runnersList.get(0).getTask().getProgressDescription();
    }

}
