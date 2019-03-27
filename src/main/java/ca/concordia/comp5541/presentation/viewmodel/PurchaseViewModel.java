package ca.concordia.comp5541.presentation.viewmodel;

import ca.concordia.comp5541.model.SubExpense;
import ca.concordia.comp5541.model.PaymentMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PurchaseViewModel extends ExpenseViewModel {
    private PaymentMethod paymentMethod;
    private Date dueDate;

    public PurchaseViewModel() {
        super();
        paymentMethod = PaymentMethod.CREDIT;
    }

    public PurchaseViewModel(UUID id,
                            Date date,
                            String description,
                            double amount,
                            boolean paid,
                            PaymentMethod paymentMethod,
                            Date dueDate,
                             ArrayList<SubExpense> subExpenses) {
        super(id, date, description, amount, paid, "Purchase", subExpenses);

        this.paymentMethod = paymentMethod;
        this.dueDate = dueDate;
    }

    public Date getDueDate() { return dueDate; }

    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
