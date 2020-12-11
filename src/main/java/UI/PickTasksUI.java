package UI;

import UI.menuType.PickObjectsMenu;
import WebCrawler.CrawlerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PickTasksUI implements PickObjectsMenu {
    private static PickTasksUI instance;

    private PickTasksUI() {}

    public static PickTasksUI getPickTaskUI() {
        if (instance == null) {
            synchronized (CrawlerManager.class) {
                if (instance == null)
                    instance = new PickTasksUI();
            }
        }
        return instance;
    }



    @Override
    public String getMenuTitle() {
        return "选择要添加的任务";
    }

    private static String[] items;
    private static boolean[] currentPickState; // 记录每个item是否被选上
    @Override
    public void getItems() {
        if (items == null) {
            CrawlerManager crawlerManager = CrawlerManager.getCrawlerManager();
            items = crawlerManager.getSupportedTasksName();
            currentPickState = new boolean[items.length];
        }
    }

    @Override
    public void checkOption(int opId) {
        currentPickState[opId] = !currentPickState[opId]; // 取反
    }

    @Override
    public int[] getPickResult() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < currentPickState.length; i++) {
            if (currentPickState[i]) {
                result.add(i);
            }
        }
        int[] ret = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ret[i] = result.get(i);
        }
        return ret;
    }


    @Override
    public void handleInteraction() {
        getItems();
        System.out.println(getMenuTitle());
        Scanner cmdScan = new Scanner(System.in);
        while(true) {
            for (int i = 0; i < items.length; i++) {
                System.out.printf("[%s] %d. %s\n", currentPickState[i] ? "✔" : " ", i, items[i]);
            }
            System.out.println("输入要添加的任务编号, 输入-1确认选择");
            int opId = 0;
            while(true) {
                try {
                    opId = Integer.parseInt(cmdScan.nextLine()); // 转换成switch-case对应的case
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("请输入正确的数字");
                }
            }

            if(opId == -1) {
                break;
            }

            if (opId >= 0 && opId < items.length) {
                checkOption(opId);
            }else {
                System.out.println("输入数字范围错误,请重试");
            }
        }
    }
}
