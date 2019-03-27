package ca.concordia.comp5541.controller;

import ca.concordia.comp5541.business.ExpenseBusiness;
import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.Expense;
import ca.concordia.comp5541.model.Purchase;
import ca.concordia.comp5541.presentation.viewmodel.BillViewModel;
import ca.concordia.comp5541.presentation.viewmodel.ExpenseViewModel;
import ca.concordia.comp5541.presentation.viewmodel.PurchaseViewModel;
import ca.concordia.comp5541.utils.FormatHelper;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ExpenseController {
    protected ExpenseBusiness expenseBusiness = ExpenseBusiness.getInstance();

    public Map<String, Class> getColumnsMetadata() {
        Map<String, Class> columnsMetadata = new LinkedHashMap<>();

        columnsMetadata.put("Date", Date.class);
        columnsMetadata.put("Description", String.class);
        columnsMetadata.put("Amount", Double.class);
        columnsMetadata.put("Paid", Boolean.class);
        columnsMetadata.put("Type", String.class);

        return columnsMetadata;
    }

    public List<ExpenseViewModel> getList() {
        List<ExpenseViewModel> bills = expenseBusiness.fetchBills().stream().map(e ->
                new BillViewModel(
                        e.getId(),
                        e.getDate(),
                        e.getDescription(),
                        e.getAmount(),
                        e.getPaid(),
                        e.getInterval())).collect(Collectors.toList());

        List<ExpenseViewModel> purchases = expenseBusiness.fetchPurchases().stream().map(e ->
                new PurchaseViewModel(
                        e.getId(),
                        e.getDate(),
                        e.getDescription(),
                        e.getAmount(),
                        e.getPaid(),
                        e.getPaymentMethod(),
                        e.getDueDate())).collect(Collectors.toList());

        return ListUtils.union(bills, purchases);
    }

    public String getTotal() {
        double total = getList().stream().mapToDouble(r -> r.getAmount()).sum();
        return FormatHelper.numberToCurrency(total);
    }

    public void pay(ExpenseViewModel viewModel) {
        Expense model = viewModel instanceof BillViewModel
                ? expenseBusiness.fetchBill(viewModel.getId()) : expenseBusiness.fetchPurchase(viewModel.getId());

        model.setPaid(true);
        expenseBusiness.save(model);
    }

    public boolean delete(ExpenseViewModel viewModel) {
        Expense model = viewModel instanceof BillViewModel ? new Bill() : new Purchase();
        model.setId(viewModel.getId());

        return expenseBusiness.delete(model);
    }
}
