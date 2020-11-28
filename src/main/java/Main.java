import DB.DBManager;
import DB.DataLine;
import UI.MainUI;
import UI.menuType.OptionsListMenu;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        MainUI ui = MainUI.getMainUI();
        ui.uiStartUp();

        DBManager dbManager = DBManager.getDBManager();
        dbManager.setupDB();

        Scanner cmdScan = new Scanner(System.in);
        ui.handleInteraction();
    }
}
