package ca.concordia.comp5541.presentation.formatting;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.Format;
import java.text.SimpleDateFormat;

public class FormatRenderer extends DefaultTableCellRenderer {
    private Format formatter;

    public FormatRenderer(Format formatter) {
        this.formatter = formatter;
    }

    public void setValue(Object value) {
        try {
            if (value != null)
                value = formatter.format(value);
        }
        catch(IllegalArgumentException e) {}

        super.setValue(value);
    }

    public static FormatRenderer getDateTimeRenderer() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, YYYY");
        return new FormatRenderer(dateFormat);
    }
}
