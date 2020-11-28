package UI.menuType;

public interface PickObjectsMenu extends Menu{
    String[] getItems();

    void checkOption(int opId);

    int[] pickResult();
}
