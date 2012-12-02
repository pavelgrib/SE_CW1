package com.acmetelecom;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class LineItem {
    private Call call;
    private BigDecimal callCost;

    public LineItem(Call call, BigDecimal callCost) {
        this.call = call;
        this.callCost = callCost;
    }

    public String date() {
        return call.date();
    }

    public String callee() {
        return call.callee();
    }

    public String durationMinutes() {
        return "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60);
    }

    public BigDecimal cost() {
        return callCost;
    }
}
