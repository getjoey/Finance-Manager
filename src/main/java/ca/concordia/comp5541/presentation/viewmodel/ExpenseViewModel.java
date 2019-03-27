package ca.concordia.comp5541.presentation.viewmodel;

import ca.concordia.comp5541.model.SubExpense;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class ExpenseViewModel {
    private UUID id;
    private Date date;
    private String description;
    private double amount;
    private boolean paid;
    private String type;
    //new inc 2
    private ArrayList<SubExpense> subExpenses = new ArrayList<SubExpense>();

    public ExpenseViewModel(UUID id, Date date, String description, double amount, boolean paid, String type, ArrayList<SubExpense> subExpenses) { //added subExpenses
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        this.type = type;
        //new code inc 2
        this.subExpenses = subExpenses;
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

        //new code inc 2...
        //if it has subExpenses need to make amount = to subExpenses
        this.amount = 0;
        if(this.subExpenses.size() > 0)
        {
            for (int i = 0; i<this.subExpenses.size(); i++)
            {
                this.amount += this.subExpenses.get(i).getAmount();
            }
        }
        else{
            this.amount = amount;
        }

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

    //new code INC 2
    public ArrayList<SubExpense> getSubExpenses() {
        return subExpenses;
    }
    public void setSubExpenses(ArrayList<SubExpense> subExpenses) {
        this.subExpenses = subExpenses;
    }
}

