package ca.concordia.comp5541.presentation.formatting;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PurchaseTableViewFormatter extends ExpenseTableViewFormatter {

    public PurchaseTableViewFormatter(JTable table) {
        super(table);
    }

    @Override
    public void formatColumns() {
        super.formatColumns();

        TableColumnModel columnModel = getColumnModel();
        TableColumn paymentMethodColumn = columnModel.getColumn(4);
        TableColumn dueDateColumn = columnModel.getColumn(5);

        dueDateColumn.setCellRenderer(CurrencyRenderer.getDateTimeRenderer());

        paymentMethodColumn.setPreferredWidth(100);
        dueDateColumn.setPreferredWidth(90);
    }
}
