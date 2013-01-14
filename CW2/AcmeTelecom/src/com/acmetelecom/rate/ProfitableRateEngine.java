package com.acmetelecom.rate;

import com.acmetelecom.Call;
import com.acmetelecom.Cost;
import com.acmetelecom.customer.Tariff;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-28
 * Time: 下午12:16
 */
public class ProfitableRateEngine implements RateEngine {

    private int start = 7;
    private int end = 19;

    public ProfitableRateEngine(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public Cost calculateCost(Call call, Tariff tariff) {
            BigDecimal cost = BigDecimal.ZERO;
            DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
            if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime()) && call.durationSeconds() < 12 * 60 * 60) {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
            } else {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
            }
        cost = cost.setScale(0, RoundingMode.HALF_UP);
        return new Cost(cost);
    }
}
