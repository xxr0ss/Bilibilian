package UI;

import DB.DBManager;
import UI.menuType.OptionsListMenu;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;
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
        return "Data saved in the database";
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
        DBManager dbManager = DBManager.getDBManager();

        String[] datalines = dbManager.readDataLine();

        List<String> trimedDatalines = new ArrayList<>();
        for( String s: datalines) {
            String t;
            if (s.length() > 70) {
                t = s.substring(0, 65) + "  ...";
            } else {
                t = s;
            }
            trimedDatalines.add(t);
        }

        Table t = Table.create("raw data")
                .addColumns(StringColumn.create("data", trimedDatalines));
        if (t.rowCount() == 0) {
            System.out.println("No data right now");
        } else {
            System.out.println(t.first(t.rowCount()));
        }
    }

    private void showStatistics1() {

    }
}
