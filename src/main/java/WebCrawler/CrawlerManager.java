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
    public String[] getSupportedTasksName() {
        List<String> tasksName = new ArrayList<>();
        for(Task t: tasks) {
            String name = t.getClass().getSimpleName();
            tasksName.add(name.substring(0, name.length()-4)); // 去掉尾部类名的"Task"
        }
        return tasksName.toArray(new String[0]);
    }


    // TaskRunner Related

    private final List<TaskRunner> runnersList = new ArrayList<>();

    public List<TaskRunner> getRunnersList() {
        return runnersList;
    }

    /**
     * 往Runner列表添加计划task
     * @param taskId 支持的task在getSupportedTasks() 数组的下标
     */
    public void buildTaskRunnerFromTaskId(int taskId) {
        int index = runnersList.size();
        TaskRunner runner = new TaskRunner(tasks[taskId]);
        runnersList.add(runner);
    }

    /**
     * 完成所有Runner的添加（和配置）后，一次性提交。
     */
    public void commitAllTaskRunners() {
        for (TaskRunner runner: runnersList) {
            String runnerStatus = runner.getStatusDescription();
            if (runnerStatus.equals(StatusConstants.constDescription[StatusConstants.NOT_STARTED]) ||
            runnerStatus.equals(StatusConstants.constDescription[StatusConstants.TERMINATED]))
            {
                Thread t = new Thread(runner);
                t.start();
            } else {
                // 虽然讲道理这里不应该出现输出语句的，不是UI模块，不过暂且这样写了
                System.out.println(runner.getTaskName() + " 暂时无法提交");
            }
        }
    }
}
