import UI.MainUI;
import WebCrawler.CrawlerManager;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        CrawlerManager crawlerManager = CrawlerManager.getCrawlerManager();
//
//        System.out.println(Arrays.toString(crawlerManager.getSupportedTasks()));
//
//        crawlerManager.addTaskRunner(0);
//        crawlerManager.addTaskRunner(1);
//        System.out.println(Arrays.toString(crawlerManager.getRunnersTaskStatus()));
//        crawlerManager.commitAllRunners();
//        Thread.sleep(1000);
//        System.out.println(Arrays.toString(crawlerManager.getRunnersTaskStatus()));
//        Thread.sleep(10000);
//        System.out.println(crawlerManager.tempTestQueryProgress());


        MainUI ui = MainUI.getMainUI();
        ui.uiStartUp();
    }
}
