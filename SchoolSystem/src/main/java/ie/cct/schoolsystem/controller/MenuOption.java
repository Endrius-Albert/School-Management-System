package ie.cct.schoolsystem.controller;

public enum MenuOption {
    SORT("SORT"),
    SEARCH("SEARCH"),
    ADD_RECORDS("ADD RECORDS"),
    CREATE_BINARY_TREE("Create a binary tree"),
    EXIT("EXIT");

    private final String label;

    MenuOption(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getNumber() {
        return ordinal() + 1;
    }

    public static MenuOption fromNumber(int number) {
        for (MenuOption option : MenuOption.values()) {
            if (option.getNumber() == number) {
                return option;
            }
        }
        return null;
    }
}
