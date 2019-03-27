package ca.concordia.comp5541.presentation.formatting;


import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class BillTableViewViewFormatter extends ExpenseTableViewFormatter {

    public BillTableViewViewFormatter(JTable table) {
        super(table);
    }

    @Override
    public void formatColumns() {
        super.formatColumns();

        TableColumnModel columnModel = getColumnModel();
        TableColumn intervalColumn = columnModel.getColumn(4);

        intervalColumn.setPreferredWidth(110);
    }
}
