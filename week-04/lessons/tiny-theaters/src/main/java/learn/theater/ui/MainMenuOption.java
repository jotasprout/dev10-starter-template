package learn.theater.ui;

public enum MainMenuOption {
    EXIT("Exit"),
    BOOK_TICKET("Book a ticket"),
    DISPLAY_PERFORMANCE_TICKETS("Display tickets for a performance");

    private String label;

    public String getLabel() {
        return label;
    }

    MainMenuOption(String label) {
        this.label = label;
    }
}
