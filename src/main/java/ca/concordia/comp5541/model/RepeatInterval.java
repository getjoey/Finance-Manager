package ca.concordia.comp5541.model;

public enum RepeatInterval {
    NEVER("Never", 0),
    WEEKLY("Every week", 1),
    BIWEEKLY("Every two weeks", 2),
    MONTHLY("Every month", 3),
    ANNUALLY("Every year", 4);

    private String text;
    private int value;

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    RepeatInterval(String text, int value) {
        this.text = text;
        this.value = value;
    }

    @Override
    public String toString() {
        return text;
    }
}
