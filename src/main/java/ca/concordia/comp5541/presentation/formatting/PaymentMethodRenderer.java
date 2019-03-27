package ca.concordia.comp5541.presentation.formatting;

import ca.concordia.comp5541.model.PaymentMethod;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PaymentMethodRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            PaymentMethod interval = (PaymentMethod) value;
            super.setText(interval.getText());
        }

        return this;
    }
}
