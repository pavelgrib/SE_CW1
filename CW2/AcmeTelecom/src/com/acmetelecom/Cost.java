package com.acmetelecom;

import java.math.BigDecimal;

/*
Tiny type to encapsulate the cost variable which is a BigDecimal type
 */
public class Cost {
    private BigDecimal callCost;

    public Cost(BigDecimal callCost) {
        this.callCost = callCost;
    }

    public BigDecimal getCallCost() {
        return callCost;
    }

    public Cost add(Cost num){
        callCost = callCost.add(num.getCallCost());
        return this;
    }

    public boolean equals(Cost otherCost) {
        return this.getCallCost().equals(otherCost.getCallCost());
    }
}
