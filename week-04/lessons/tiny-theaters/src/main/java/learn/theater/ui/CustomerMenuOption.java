package learn.theater.ui;

public enum CustomerMenuOption {
    EXIT_MENU("Exit menu"),
    CREATE_NEW_CUSTOMER("Create a new customer"),
    SEARCH_EXISTING("Search for an existing customer");
    private  String label;

    public String getLabel() {
        return label;
    }

    CustomerMenuOption(String label) {
        this.label = label;
    }
}
