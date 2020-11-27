package UI;

import UI.menuType.Menu;
import UI.menuType.OptionsListMenu;
import WebCrawler.CrawlerManager;

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


    @Override
    public String getMenuName() {
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

    }
}
