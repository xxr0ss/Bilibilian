package UI;

import UI.menuType.Menu;
import UI.menuType.PickObjectsMenu;
import WebCrawler.CrawlerManager;

import java.util.ArrayList;
import java.util.List;

public class PickTasksUI implements PickObjectsMenu {
    @Override
    public String getMenuTitle() {
        return "- 选择要添加的任务";
    }

    private static String[] items;
    private static boolean[] picked;
    @Override
    public String[] getItems() {
        if (items == null) {
            CrawlerManager crawlerManager = CrawlerManager.getCrawlerManager();
            items = crawlerManager.getSupportedTasks();
        }
        picked = new boolean[items.length];
        return items;
    }

    @Override
    public void checkOption(int opId) {
        picked[opId] = !picked[opId]; // 取反
    }

    @Override
    public int[] pickResult() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < picked.length; i++) {
            if (picked[i]) {
                result.add(i);
            }
        }
        int[] ret = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ret[i] = result.get(i);
        }
        return ret;
    }
}
