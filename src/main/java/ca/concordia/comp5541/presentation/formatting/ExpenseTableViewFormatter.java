package ca.concordia.comp5541.presentation.formatting;


import ca.concordia.comp5541.model.PaymentMethod;
import ca.concordia.comp5541.model.RepeatInterval;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ExpenseTableViewFormatter {
    private final JTable table;
    private TableColumnModel columnModel;

    protected JTable getTable() {
        return table;
    }

    protected TableColumnModel getColumnModel() {
        return columnModel;
    }

    public ExpenseTableViewFormatter(JTable table) {
        this.table = table;
        this.columnModel = table.getColumnModel();
    }

    public void format() {
        formatColumns();

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setDefaultRenderer(RepeatInterval.class, new RepeatIntervalRenderer());
        table.setDefaultRenderer(PaymentMethod.class, new PaymentMethodRenderer());

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBorder(new LineBorder(new Color(230, 230, 230)));
        header.setBackground(new Color(230, 230, 230));

        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    protected void formatColumns() {
        TableColumn dateColumn = columnModel.getColumn(0);
        TableColumn descriptionColumn = columnModel.getColumn(1);
        TableColumn amountColumn = columnModel.getColumn(2);
        TableColumn paidColumn = columnModel.getColumn(3);

        dateColumn.setCellRenderer(CurrencyRenderer.getDateTimeRenderer());
        amountColumn.setCellRenderer(CurrencyRenderer.getCurrencyRenderer());

        dateColumn.setPreferredWidth(90);
        descriptionColumn.setPreferredWidth(220);
        amountColumn.setPreferredWidth(100);
        paidColumn.setPreferredWidth(36);
    }
}
