package com.acmetelecom;

import java.math.BigDecimal;


public class MoneyFormatter {

    //constructor refactored so that a Cost type is used as a parameter
    public static String penceToPounds(Cost pence) {
        BigDecimal pounds = pence.getCallCost().divide(new BigDecimal(100));
        return String.format("%.2f", pounds.doubleValue());
    }
}
