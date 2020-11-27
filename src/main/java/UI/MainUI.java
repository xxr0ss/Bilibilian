package UI;

import UI.menuType.OptionsListMenu;
import WebCrawler.CrawlerManager;
import WebCrawler.TaskRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MainUI implements OptionsListMenu {
    private static MainUI instance;

    private CrawlerManager crawlerManager;

    private MainUI() {

    }

    public static MainUI getMainUI() {
        if (instance == null) {
            synchronized (CrawlerManager.class) {
                if (instance == null)
                    instance = new MainUI();
            }
        }
        return instance;
    }


    private void showWelcome() {
        System.out.println(
                "\n" +
                        "   ██████╗  ██╗ ██╗      ██╗ ██████╗  ██╗ ██╗      ██╗  █████╗  ███╗   ██╗\n" +
                        "   ██╔══██╗ ██║ ██║      ██║ ██╔══██╗ ██║ ██║      ██║ ██╔══██╗ ████╗  ██║\n" +
                        "   ██████╔╝ ██║ ██║      ██║ ██████╔╝ ██║ ██║      ██║ ███████║ ██╔██╗ ██║\n" +
                        "   ██╔══██╗ ██║ ██║      ██║ ██╔══██╗ ██║ ██║      ██║ ██╔══██║ ██║╚██╗██║\n" +
                        "   ██████╔╝ ██║ ███████╗ ██║ ██████╔╝ ██║ ███████╗ ██║ ██║  ██║ ██║ ╚████║\n" +
                        "   ╚═════╝  ╚═╝ ╚══════╝ ╚═╝ ╚═════╝  ╚═╝ ╚══════╝ ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═══╝"
        );
    }

    public void uiStartUp() {
        showWelcome();
    }


    @Override
    public String getMenuName() {
        return "Main Menu";
    }

    private final String[] options = new String[]{
            "查看当前支持的爬取目标",
            "添加爬取目标到任务列表",
            "执行已添加任务",
            "查看任务状态",
            "控制爬取任务",
            "退出Bilibilian",
    };

    @Override
    public String[] getMenuOptions() {
        return options;
    }

    /**
     * 通过一个switch case来处理getMemuOptions里面支持的菜单选项
     *
     * @param optionIdx 选项编号（数组下标）
     */
    @Override
    public void performOption(int optionIdx) {
        assert (optionIdx >= 0 && optionIdx < options.length);
        crawlerManager = CrawlerManager.getCrawlerManager(); // 保证执行选项时，crawlerManager已经拿到实例
        switch (optionIdx) {
            case 0:
                showSupportedTasks();
                break;

            case 1:
                // TODO: 创建个taskAdder类？
                break;

            case 2:
                execTaskRunnerList();
                break;

            case 3:
                showTaskRunnersStatus();
                break;

            case 4:
                controlTaskRunner();
                break;

            case 5:
                wantToExit = true;
                break;
            default:
                System.out.println("Not implemented");
        }
    }


    private boolean wantToExit = false;

    /* 各个case的具体实现 START */
    private void pressAnyKey() {
        try {
            System.out.println("按任意键返回");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // case 0: DONE
    private void showSupportedTasks() {
        System.out.println(Arrays.toString(crawlerManager.getSupportedTasks()));
        pressAnyKey();
    }

    // case 1
    private void pickIntoTaskRunner() {
        // TODO: 实现PickObjectsMenu来处理勾选类操作
    }

    // case 2: DONE
    private void execTaskRunnerList() {
        if (crawlerManager.getRunnersList().size() == 0) {
            System.out.println("尚未添加爬取任务");
            return;
        }
        crawlerManager.commitAllTaskRunners();
    }

    // case 3: TODO 优化显示效果
    private void showRunnersList() {
        TaskRunner[] runnersList = crawlerManager.getRunnersList().toArray(new TaskRunner[0]);
        if (runnersList.length == 0) {
            System.out.println("尚无添加爬取任务");
        }
        System.out.println("Index      Name               Status");
        for (int i = 0; i < runnersList.length; i++) {
            TaskRunner runner = runnersList[i];
            System.out.printf("%-4d %20s  %15s\n", i, runner.getName(), runner.getStatusDescription());
        }
    }

    private void showTaskRunnersStatus() {
        showRunnersList();
        pressAnyKey();
    }

    // case 4
    private void controlTaskRunner() {
        // 展示可以用于操作的Runner
        showTaskRunnersStatus();
        if (crawlerManager.getRunnersList().size() == 0) {
            return;
        }
        System.out.println("请输入要执行操作的任务编号");
        Scanner scan = new Scanner(System.in);
        int idx = scan.nextInt();
        if (idx >= 0 && idx < crawlerManager.getRunnersList().size()) {
            System.out.printf("你选择了编号为%d的爬取任务\n", idx);
            TaskRunnerControlUI ui = TaskRunnerControlUI.getControlUI();

        }
    }


    // case 5: DONE
    public boolean isWantToExit() {
        return wantToExit;
    }

    /* 各个case的具体实现 END */
}
