package ca.concordia.comp5541.model;

import java.util.Date;

public class Purchase extends Expense {
    private PaymentMethod paymentMethod;
    private Date dueDate;

    public Purchase() {
        paymentMethod = PaymentMethod.CREDIT;
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
