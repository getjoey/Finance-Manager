package ca.concordia.comp5541.model;

import java.util.ArrayList;

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
