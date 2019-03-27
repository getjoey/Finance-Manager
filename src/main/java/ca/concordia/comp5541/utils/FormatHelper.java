package ca.concordia.comp5541.utils;

import javax.swing.text.InternationalFormatter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FormatHelper {
    public static InternationalFormatter getCurrencyFormatter() {
        NumberFormat format = DecimalFormat.getCurrencyInstance();

        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);

        InternationalFormatter formatter = new InternationalFormatter(format);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);

        return formatter;
    }

    public static String numberToCurrency(double number) {
        NumberFormat format = DecimalFormat.getCurrencyInstance();

        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);

        return format.format(number);
    }
}
