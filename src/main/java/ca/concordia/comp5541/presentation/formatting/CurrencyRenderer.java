package ca.concordia.comp5541.presentation.formatting;

import javax.swing.*;
import java.text.NumberFormat;

public class CurrencyRenderer extends FormatRenderer {
    public CurrencyRenderer(NumberFormat formatter) {
        super(formatter);
        setHorizontalAlignment( SwingConstants.RIGHT );
    }

    public static CurrencyRenderer getCurrencyRenderer() {
        return new CurrencyRenderer(NumberFormat.getCurrencyInstance());
    }
}
