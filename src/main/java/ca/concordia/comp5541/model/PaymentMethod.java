package ca.concordia.comp5541.model;

public enum PaymentMethod {
    CREDIT("Credit Card", 0),
    CASH("Cash", 1),
    DEBIT("Debit", 2);

    private String text;
    private int value;

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    PaymentMethod(String text, int value) {
        this.text = text;
        this.value = value;
    }

    @Override
    public String toString() {
        return text;
    }
}
