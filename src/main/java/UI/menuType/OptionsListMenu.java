package UI.menuType;

public interface OptionsListMenu extends Menu {
    String[] getMenuOptions();

    void performOption(int optionIdx);
}
