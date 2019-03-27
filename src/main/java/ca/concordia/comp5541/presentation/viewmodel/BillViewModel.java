package ca.concordia.comp5541.presentation.viewmodel;

import ca.concordia.comp5541.model.RepeatInterval;

import java.util.Date;
import java.util.UUID;

public class BillViewModel extends ExpenseViewModel {
    private RepeatInterval interval;

    public BillViewModel() {
        super();
        interval = RepeatInterval.NEVER;
    }

    public BillViewModel(UUID id,
                         Date date,
                         String description,
                         double amount,
                         boolean paid,
                         RepeatInterval interval) {
        super(id, date, description, amount, paid, "Bill");

        this.interval = interval;
    }

    public RepeatInterval getInterval() {
        return interval;
    }

    public void setInterval(RepeatInterval interval) {
        this.interval = interval;
    }
}
