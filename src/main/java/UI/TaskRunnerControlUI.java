package UI;

import UI.menuType.Menu;
import UI.menuType.OptionsListMenu;
import WebCrawler.CrawlerManager;
import WebCrawler.TaskRunner;

import java.util.Scanner;

public class TaskRunnerControlUI implements Menu, OptionsListMenu {
    private static TaskRunnerControlUI instance;

    private TaskRunnerControlUI() {}

    public static TaskRunnerControlUI getControlUI() {
        if (instance == null) {
            synchronized (CrawlerManager.class) {
                if (instance == null)
                    instance = new TaskRunnerControlUI();
            }
        }
        return instance;
    }

    private int taskRunnerId = -1;

    public void setTaskRunnerId(int taskRunnerId) {
        this.taskRunnerId = taskRunnerId;
    }

    @Override
    public String getMenuTitle() {
        return "\n-- 从列表中选择进行操作的任务";
    }


    private final String[] options = new String[]{
            "暂停",
            "继续",
            "终止"
    };
    @Override
    public String[] getMenuOptions() {
        return options;
    }

    @Override
    public void performOption(int optionIdx) {
        TaskRunner taskRunner = CrawlerManager.getCrawlerManager().getRunnersList().get(taskRunnerId);
        switch (optionIdx) {
            case 0:
                if (taskRunner.isPaused()) {
                    System.out.println("已处于暂停状态");
                    break;
                }
                taskRunner.wantToPause(true);
                break;

            case 1:
                if (taskRunner.isPaused()) {
                    taskRunner.wantToPause(false);
                } else {
                    System.out.println("任务不处于暂停状态");
                }
                break;

            case 2:
                taskRunner.wantToTerminate();
                System.out.println("任务已停止");
                break;

            default:
                System.out.println("Not supported operation");
                break;
        }
    }

    private void showOptions() {
        String[] options = getMenuOptions();
        for (int i = 1; i <= options.length; i++) {
            System.out.printf("[%d] %s\n", i, options[i-1]);
        }
        System.out.println();
    }

    @Override
    public void handleInteraction() {
        Scanner cmdScan = new Scanner(System.in);
        showOptions();
        while (true) {
            System.out.print("> ");
            try {
                int cmd = Integer.parseInt(cmdScan.nextLine()); // 转换成switch-case对应的case
                if (cmd > 0 && cmd <= options.length) {
                    performOption(cmd - 1);
                    break;
                } else {
                    System.out.println("输入的数字范围错误, 请重试");
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入正确的数字");
            }
        }
    }

}
