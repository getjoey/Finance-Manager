package ca.concordia.comp5541.presentation.formatting;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class SubExpenseTableViewFormatter {
    //{ "Description", "Amount", "Paid" };
    private final JTable table;
    private TableColumnModel columnModel;

    protected JTable getTable() {
        return table;
    }

    protected TableColumnModel getColumnModel() {
        return columnModel;
    }

    public SubExpenseTableViewFormatter(JTable table) {
        this.table = table;
        this.columnModel = table.getColumnModel();
    }

    public void format() {
        formatColumns();

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBorder(new LineBorder(new Color(230, 230, 230)));
        header.setBackground(new Color(230, 230, 230));

        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //{ "Description", "Amount", "Paid" };
    protected void formatColumns() {
        TableColumn descriptionColumn = columnModel.getColumn(0);
        TableColumn amountColumn = columnModel.getColumn(1);
        TableColumn paidColumn = columnModel.getColumn(2);

        amountColumn.setCellRenderer(CurrencyRenderer.getCurrencyRenderer());

        descriptionColumn.setPreferredWidth(270);
        amountColumn.setPreferredWidth(100);
        paidColumn.setPreferredWidth(36);
    }
}
