package ca.concordia.comp5541.presentation.viewmodel;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Method;
import java.util.*;

public class ExpenseTableViewModel extends AbstractTableModel {
    private List<ExpenseViewModel> data;
    private String[] columnNames;
    private Class[] columnClasses;
    private Method getterMethod;

    public ExpenseTableViewModel(List<ExpenseViewModel> data, Map<String, Class> columnsMetadata) {
        this.data = data;

        columnNames = new String[columnsMetadata.size()];
        columnClasses = new Class[columnsMetadata.size()];

        int index = 0;

        for (String key : columnsMetadata.keySet()) {
            columnNames[index] = key;
            columnClasses[index] = columnsMetadata.get(key);
            index++;
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String columnName = columnNames[columnIndex].replace(" ", "");
        ExpenseViewModel currentObj = data.get(rowIndex);

        try {
            getterMethod = currentObj.getClass().getMethod("get" + columnName);

            return getterMethod.invoke(currentObj);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnClasses[column];
    }



    public ExpenseViewModel getDataAtRow(int rowIndex) {
        return rowIndex >= 0 ? data.get(rowIndex) : null;
    }


    //new code inc2
    public void removeRow(int row) {
        // remove a row from your internal data structure
        this.data.remove(row);
    }
}
