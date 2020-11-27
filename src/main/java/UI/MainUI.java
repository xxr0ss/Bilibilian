package UI;

import WebCrawler.CrawlerManager;

public class MainUI {
    private static MainUI instance;

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
}
