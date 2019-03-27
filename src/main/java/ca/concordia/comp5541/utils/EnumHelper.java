package ca.concordia.comp5541.utils;

import ca.concordia.comp5541.model.PaymentMethod;
import ca.concordia.comp5541.model.RepeatInterval;

public class EnumHelper {
    public static RepeatInterval intervalFromValue(int value) {

        for (RepeatInterval interval : RepeatInterval.values()) {
            if (interval.getValue() == value) {
                return interval;
            }
        }

        return RepeatInterval.NEVER;
    }

    public static PaymentMethod paymentMethodFromValue(int value) {

        for (PaymentMethod interval : PaymentMethod.values()) {
            if (interval.getValue() == value) {
                return interval;
            }
        }

        return PaymentMethod.CREDIT;
    }
}
