package ca.concordia.comp5541.controller;

import ca.concordia.comp5541.model.PaymentMethod;
import ca.concordia.comp5541.model.Purchase;
import ca.concordia.comp5541.presentation.viewmodel.ExpenseViewModel;
import ca.concordia.comp5541.presentation.viewmodel.PurchaseViewModel;

import java.util.*;
import java.util.stream.Collectors;

public class PurchaseController extends ExpenseController {



    @Override
    public Map<String, Class> getColumnsMetadata() {
        Map<String, Class> columnsMetadata = new LinkedHashMap<>();

        columnsMetadata.put("Date", Date.class);
        columnsMetadata.put("Description", String.class);
        columnsMetadata.put("Amount", Double.class);
        columnsMetadata.put("Paid", Boolean.class);
        columnsMetadata.put("Payment Method", PaymentMethod.class);
        columnsMetadata.put("Due Date", Date.class);

        return columnsMetadata;
    }

    @Override
    public List<ExpenseViewModel> getList() {
        return expenseBusiness.fetchPurchases().stream().map(e ->
                new PurchaseViewModel(
                        e.getId(),
                        e.getDate(),
                        e.getDescription(),
                        e.getAmount(),
                        e.getPaid(),
                        e.getPaymentMethod(),
                        e.getDueDate(),
                        e.getSubExpenses())).collect(Collectors.toList());
    }

    public void save(PurchaseViewModel viewModel) {
        Purchase model = new Purchase();

        model.setId(viewModel.getId());
        model.setDate(viewModel.getDate());
        model.setDescription(viewModel.getDescription());
        model.setAmount(viewModel.getAmount());
        model.setPaid(viewModel.getPaid());
        model.setDueDate(viewModel.getDueDate());
        model.setPaymentMethod(model.getPaymentMethod());
        //new code inc 2
        model.setSubExpenses(viewModel.getSubExpenses());

        expenseBusiness.save(model);
    }


}
