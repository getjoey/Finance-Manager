package ca.concordia.comp5541.presentation.viewmodel;

import java.util.Date;
import java.util.UUID;

public abstract class ExpenseViewModel {
    private UUID id;
    private Date date;
    private String description;
    private double amount;
    private boolean paid;
    private String type;

    public ExpenseViewModel(UUID id, Date date, String description, double amount, boolean paid, String type) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        this.type = type;
    }

    public ExpenseViewModel() {
        date = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getType() {
        return type;
    }
}

