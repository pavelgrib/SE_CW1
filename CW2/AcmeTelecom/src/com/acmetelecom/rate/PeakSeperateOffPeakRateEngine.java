package com.acmetelecom.rate;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Tariff;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-28
 * Time: 下午12:02
 */
public class PeakSeperateOffPeakRateEngine implements RateEngine {

    private int startHour;
    private int endHour;

    public PeakSeperateOffPeakRateEngine(){
        new PeakSeperateOffPeakRateEngine(7,19);
    }

    public PeakSeperateOffPeakRateEngine(int startHour, int endHour){
        this.startHour = startHour;
        this.startHour = endHour;
    }

    @Override
    public BigDecimal calculateCost(Call call,Tariff tariff) {
        BigDecimal cost = BigDecimal.ZERO;


        return null;
    }
}
