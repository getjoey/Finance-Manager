package ca.concordia.comp5541.model;

import java.util.UUID;

public class SubExpense {

    private String description;
    private double amount;
    private boolean paid;
    private UUID id;


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

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
