package ca.concordia.comp5541.presentation.viewmodel;

import ca.concordia.comp5541.model.SubExpense;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SubExpenseTableViewModel extends AbstractTableModel {

    private Method getterMethod;
    private ArrayList<SubExpense> data = new ArrayList<SubExpense>();


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] columnNames = getColumnNames();
        String columnName = columnNames[columnIndex].replace(" ", "");
        Object currentObj = data.get(rowIndex);

        try {
            getterMethod = currentObj.getClass().getMethod("get" + columnName);

            return getterMethod.invoke(currentObj);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        return getColumnNames()[col];
    }

    @Override
    public Class getColumnClass(int column) {
        return getColumnClasses()[column];
    }

    public void bindTableData(ArrayList<SubExpense> expenseSubExpenses) {
        this.data = expenseSubExpenses;

        fireTableDataChanged();
    }

    public String[] getColumnNames() {
        String[] columnNames = { "Description", "Amount", "Paid" };
        return columnNames;
    }

    public Class[] getColumnClasses() {
        Class[] columnClasses = { String.class, Double.class, Boolean.class };
        return columnClasses;
    }

    public SubExpense getDataAtRow(int rowIndex) {
        return rowIndex >= 0 ? data.get(rowIndex) : null;
    }






}
