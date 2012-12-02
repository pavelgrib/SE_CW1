package com.acmetelecom;

import java.math.BigDecimal;

public class MoneyFormatter {
    public static String penceToPounds(Cost pence) {
        BigDecimal pounds = pence.getCallCost().divide(new BigDecimal(100));
        return String.format("%.2f", pounds.doubleValue());
    }
}
