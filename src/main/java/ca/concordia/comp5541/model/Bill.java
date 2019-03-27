package ca.concordia.comp5541.model;

public class Bill extends Expense {
    private RepeatInterval interval;

    public Bill() {
        interval = RepeatInterval.NEVER;
    }

    public RepeatInterval getInterval() {
        return interval;
    }

    public void setInterval(RepeatInterval interval) {
        this.interval = interval;
    }
}
