import UI.MainUI;
import UI.menuType.OptionsListMenu;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        MainUI ui = MainUI.getMainUI();
        ui.uiStartUp();
        ui.handleInteraction();
    }
}
