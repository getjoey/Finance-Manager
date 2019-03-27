package ca.concordia.comp5541.controller;

import ca.concordia.comp5541.model.Bill;
import ca.concordia.comp5541.model.RepeatInterval;
import ca.concordia.comp5541.presentation.viewmodel.BillViewModel;
import ca.concordia.comp5541.presentation.viewmodel.ExpenseViewModel;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillController extends ExpenseController {

    @Override
    public Map<String, Class> getColumnsMetadata() {
        Map<String, Class> columnsMetadata = new LinkedHashMap<>();

        columnsMetadata.put("Date", Date.class);
        columnsMetadata.put("Description", String.class);
        columnsMetadata.put("Amount", Double.class);
        columnsMetadata.put("Paid", Boolean.class);
        columnsMetadata.put("Interval", RepeatInterval.class);

        return columnsMetadata;
    }

    @Override
    public List<ExpenseViewModel> getList() {
        return expenseBusiness.fetchBills().stream().map(e ->
                new BillViewModel(
                        e.getId(),
                        e.getDate(),
                        e.getDescription(),
                        e.getAmount(),
                        e.getPaid(),
                        e.getInterval(),
                        e.getSubExpenses())).collect(Collectors.toList());
    }

    public void save(BillViewModel viewModel) {
        Bill model = new Bill();

        model.setId(viewModel.getId());
        model.setDate(viewModel.getDate());
        model.setDescription(viewModel.getDescription());
        model.setAmount(viewModel.getAmount());
        model.setPaid(viewModel.getPaid());
        model.setInterval(viewModel.getInterval());
        //new code inc2
        model.setSubExpenses(viewModel.getSubExpenses());

        expenseBusiness.save(model);
    }
}
