package ca.concordia.comp5541.presentation.formatting;

import ca.concordia.comp5541.model.RepeatInterval;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RepeatIntervalRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null) {
            RepeatInterval interval = (RepeatInterval) value;
            super.setText(interval.getText());
        }

        return this;
    }
}
