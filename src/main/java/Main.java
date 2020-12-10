import DB.DBManager;
import UI.MainUI;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        MainUI ui = MainUI.getMainUI();

        DBManager dbManager = DBManager.getDBManager();
        dbManager.setupDB();

        Scanner cmdScan = new Scanner(System.in);
        ui.handleInteraction();
    }
}
