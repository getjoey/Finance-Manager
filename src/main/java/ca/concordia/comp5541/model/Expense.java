package ca.concordia.comp5541.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/***
 * Represents a single expense
 */
public abstract class Expense {
    private String description;
    private double amount;
    private Date date;
    private boolean paid;
    private UUID id;


    public Expense() {
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


    //new code INC 2
    private ArrayList<SubExpense> subExpenses = new ArrayList<SubExpense>();
    public ArrayList<SubExpense> getSubExpenses() {
        return subExpenses;
    }

    public void setSubExpenses(ArrayList<SubExpense> subExpenses) {
        this.subExpenses = subExpenses;
    }

}

