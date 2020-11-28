import UI.MainUI;
import UI.menuType.OptionsListMenu;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner cmdScanner = new Scanner(System.in);

        MainUI mainUI = MainUI.getMainUI();
        mainUI.uiStartUp();

        do {
            showListMenu(mainUI);
            int option = cmdScanner.nextInt() - 1;
            mainUI.performOption(option);
        } while (!mainUI.isWantToExit());

        cmdScanner.close();
    }

    private static void showListMenu(OptionsListMenu ui) {
        System.out.println("\n* " + ui.getMenuTitle() + " *");
        String[] options = ui.getMenuOptions();
        for (int i = 1; i <= options.length; i++) {
            System.out.printf("[%d] %s\n", i, options[i-1]);
        }
        System.out.println();
    }
}
