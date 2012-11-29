package com.acmetelecom.rate;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Tariff;
import org.omg.CORBA.BAD_INV_ORDER;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-28
 * Time: 下午12:02
 */
public class PeakSeperateOffPeakRateEngine /*implements RateEngine*/ {

    // defaut peak time is 7:00 to 19:00
    private int startHour = 7;
    private int endHour = 19;

    public PeakSeperateOffPeakRateEngine(){

    }

    public PeakSeperateOffPeakRateEngine(int startHour, int endHour){
        this.startHour = startHour;
        this.endHour = endHour;
    }

    //@Override
    public BigDecimal calculateCost(Call call/*，Tariff tariff*/) {
        BigDecimal cost = BigDecimal.ZERO;
        DaytimePeakPeriod daytimePeakPeriod = new DaytimePeakPeriod(startHour, endHour);
        int startHourInSec = startHour*3600;
        int endHourInSec = endHour*3600;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(call.startTime());
        int sHour = calendar.get(Calendar.HOUR_OF_DAY);
        int sMinute = calendar.get(Calendar.MINUTE);
        int sSecond = calendar.get(Calendar.SECOND);
        int startTimeInSec = sHour*3600+sMinute*60+sSecond;

        calendar.setTime(call.endTime());
        int eHour = calendar.get(Calendar.HOUR_OF_DAY);
        int eMinute = calendar.get(Calendar.MINUTE);
        int eSecond = calendar.get(Calendar.SECOND);
        int endTimeInSec = eHour*3600+eMinute*60+eSecond;


        System.out.println(startTimeInSec);
        System.out.println("---"+startHourInSec);
        System.out.println(endTimeInSec);
        System.out.println("---"+endHourInSec);
        // we ignore the situation that a call could last over 24 hours
        // but we have to consider the call last overnight
        if (eHour < sHour) {
            int eHourOverNight = eHour+24;

        } else {


            // the call duration is all in the off peak time, we should notice that a call can last over the peak time
            if (daytimePeakPeriod.offPeak(call.startTime()) && daytimePeakPeriod.offPeak(call.endTime()) &&
                    call.durationSeconds() <= (endHourInSec - startHourInSec)) {
                cost = cost.add(new BigDecimal(call.durationSeconds()).multiply(/*tariff.offPeakRate()*/ BigDecimal.ONE));
            }
            // the call duration is all in the peak time
            else if (!daytimePeakPeriod.offPeak(call.startTime()) && !daytimePeakPeriod.offPeak(call.endTime())) {
                cost = cost.add(new BigDecimal(call.durationSeconds()).multiply(/*tariff.peakRate()*/BigDecimal.TEN));
            }
            // the call crosses both off peak and peak time
            else {
                int offPeakTime = 0;
                int peakTime = 0;
                // cross the start of the peak
                if (startTimeInSec <= startHourInSec && startHourInSec <= endTimeInSec && endTimeInSec <= endHourInSec){
                    System.out.println("cross first");
                    offPeakTime = startHourInSec-startTimeInSec ;
                    peakTime = endTimeInSec-startHourInSec;
                }
                // cross the end of the peak
                else if (startHourInSec <= startTimeInSec && startTimeInSec <= endHourInSec && endHourInSec <= endTimeInSec ){
                    System.out.println("cross last");
                    peakTime = endHourInSec - startTimeInSec;
                    offPeakTime = endTimeInSec - endHourInSec ;
                }
                // cross whole peak
                else if (sHour <= startHour && eHour >= endHour){
                    System.out.println("all cross");
                    peakTime = endHourInSec-startHourInSec;
                    System.out.println("peaktime ="+ peakTime);
                    offPeakTime = (startHourInSec-startTimeInSec) + (endTimeInSec-endHourInSec);
                    System.out.println("offpeaktiem = "+offPeakTime);
                }

                cost = cost.add(new BigDecimal(offPeakTime).multiply(/*tariff.offPeakRate()*/BigDecimal.ONE));
                cost = cost.add(new BigDecimal(peakTime).multiply(/*tariff.peakRate()*/BigDecimal.TEN));
            }
        }

        System.out.println("Cost is ---------------- "+cost);
        return cost;
    }
}
