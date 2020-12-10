package UI;

import UI.menuType.OptionsListMenu;

import java.util.Scanner;

public class ShowDataUI implements OptionsListMenu {
    private static ShowDataUI instance;

    private ShowDataUI() {
    }

    public static ShowDataUI getShowDataUI() {
        if (instance == null) {
            synchronized (ShowDataUI.class) {
                if (instance == null)
                    instance = new ShowDataUI();
            }
        }
        return instance;
    }


    @Override
    public String getMenuTitle() {
        return "* Data saved in the database *";
    }


    private void showListMenu() {
        System.out.println("\n* " + this.getMenuTitle() + " *");
        String[] options = getMenuOptions();
        for (int i = 1; i <= options.length; i++) {
            System.out.printf("[%d] %s\n", i, options[i - 1]);
        }
        System.out.println();
    }

    @Override
    public void handleInteraction() {
        Scanner cmdScan = new Scanner(System.in);
        showListMenu();
        System.out.println("\n输入选项 >");
        int opt;
        do {
            opt = cmdScan.nextInt() - 1;
        } while (opt < 0 || opt > getMenuOptions().length);

        performOption(opt);
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{
                "显示所有数据",
                "显示统计数据1"
        };
    }

    @Override
    public void performOption(int optionIdx) {
        switch (optionIdx) {
            case 0:
                showAllData();
                break;
            case 1:
                showStatistics1();
                break;
        }
    }

    private void showAllData() {

    }

    private void showStatistics1() {

    }
}
