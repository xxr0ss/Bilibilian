package UI.menuType;

public interface PickObjectsMenu extends Menu{
    void getItems();

    void checkOption(int opId);

    int[] getPickResult();
}
