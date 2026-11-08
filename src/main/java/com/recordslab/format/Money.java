package com.recordslab.format;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Rule 2: money is BigDecimal with two decimals, rounded half-even. */
public final class Money {
    private Money() {
    }

    public static BigDecimal round(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static String format(BigDecimal amount) {
        return round(amount).toPlainString();
    }

    /** A twelfth of a yearly amount. */
    public static BigDecimal monthly(BigDecimal yearly) {
        return yearly.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_EVEN);
    }
}
