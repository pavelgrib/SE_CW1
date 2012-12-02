package com.acmetelecom.rate;

import com.acmetelecom.Call;
import com.acmetelecom.Cost;
import com.acmetelecom.customer.Tariff;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;


/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-28
 * Time: 下午12:02
 */
public class PeakSeperateOffPeakRateEngine implements RateEngine {

    // defaut peak time is 7:00 to 19:00
    private int startHour = 7;
    private int endHour = 19;

    public PeakSeperateOffPeakRateEngine(){

    }

    public PeakSeperateOffPeakRateEngine(int startHour, int endHour){
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public Cost calculateCost(Call call, Tariff tariff){
        BigDecimal cost = BigDecimal.ZERO;
        int[] duration = calculateDuration(call);

        cost = cost.add(new BigDecimal(duration[0]).multiply(tariff.offPeakRate()));
        cost = cost.add(new BigDecimal(duration[1]).multiply(tariff.peakRate()));

        cost = cost.setScale(0, RoundingMode.HALF_UP);
        return new Cost(cost);
    }

    public int[] calculateDuration(Call call) {
        int[] duration = new int[2]; // int array to store peak and offpeak durations

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


//        System.out.println("startTimeInSec="+startTimeInSec);
//        System.out.println("PeakStart---"+startHourInSec);
//        System.out.println("endTimeInSEC="+endTimeInSec);
//        System.out.println("PeakEnds---"+endHourInSec);
//        System.out.println("start hour = "+sHour);
//        System.out.println("end hour = "+eHour);

        // we ignore the situation that a call could last over 24 hours
        // but we have to consider the call last overnight
        if (eHour < sHour) {
            int offPeakTime = 0;
            int peakTime = 0;
            //System.out.println("enter cross day mode");
            int endTimeInSecOverNight = endTimeInSec + 24*3600;// plus one day
            int startHourInSecOverNight = startHourInSec + 24*3600;// plus one day
            int endHourInSecOverNight = endHourInSec + 24*3600;// plus one day
            //System.out.println("change over night end hour to "+ endTimeInSecOverNight);

            // O-P-O-24-O
            if (startTimeInSec <= startHourInSec && endTimeInSecOverNight >= endHourInSec && endTimeInSecOverNight <= startHourInSecOverNight){
                peakTime = endHourInSec-startHourInSec;
                offPeakTime = endTimeInSecOverNight-startTimeInSec - peakTime;
                System.out.println("O-P-O-24-O");
            }
            // P-O-24-O
            if (startTimeInSec >= startHourInSec && startTimeInSec <= endHourInSec && endTimeInSecOverNight <= startHourInSecOverNight){
                peakTime = endHourInSec - startTimeInSec;
                offPeakTime = endTimeInSecOverNight-startTimeInSec-peakTime;
                System.out.println("P-O-24-O");
            }
            // P-O-24-O-P
            if (startTimeInSec >= startHourInSec && startTimeInSec <= endHourInSec
               && endTimeInSecOverNight >=startHourInSecOverNight && endTimeInSecOverNight <= endHourInSecOverNight){
                offPeakTime = startHourInSecOverNight - endHourInSec;
                peakTime = endTimeInSecOverNight-startTimeInSec-offPeakTime;
                System.out.println("P-O-24-O-P");
            }
            // O-24-O
            if (startTimeInSec >= endHourInSec && endTimeInSecOverNight <= startHourInSecOverNight){
               peakTime = 0;
               offPeakTime = endTimeInSecOverNight-startTimeInSec;
                System.out.println("O-24-O");
            }
            // O-24-O-P
            if (startTimeInSec >= endHourInSec && endTimeInSecOverNight >= startHourInSecOverNight && endTimeInSecOverNight <= endHourInSecOverNight){
                offPeakTime = startHourInSecOverNight-startTimeInSec;
                peakTime = endTimeInSecOverNight-startTimeInSec-offPeakTime;
                System.out.println("O-24-O-P");
            }
            // O-24-O-P-O
            if (startTimeInSec >= endHourInSec && endTimeInSecOverNight >= endHourInSecOverNight){
                peakTime = endHourInSecOverNight -startHourInSecOverNight;
                offPeakTime = endTimeInSecOverNight-startTimeInSec-peakTime;
                System.out.println("O-24-O-P-O");
            }

            duration[0] = offPeakTime;
            duration[1] = peakTime;
        } else {


            // the call duration is all in the off peak time, we should notice that a call can last over the peak time
            if (daytimePeakPeriod.offPeak(call.startTime()) && daytimePeakPeriod.offPeak(call.endTime()) &&
                    call.durationSeconds() <= (endHourInSec - startHourInSec)) {
                duration[0] = call.durationSeconds();
                duration[1] = 0;
            }
            // the call duration is all in the peak time
            else if (!daytimePeakPeriod.offPeak(call.startTime()) && !daytimePeakPeriod.offPeak(call.endTime())) {
                duration[0] = 0;
                duration[1] = call.durationSeconds();
            }
            // the call crosses both off peak and peak time
            else {
                int offPeakTime = 0;
                int peakTime = 0;

                // cross the start of the peak
                if (startTimeInSec <= startHourInSec && startHourInSec <= endTimeInSec && endTimeInSec <= endHourInSec){
                    offPeakTime = startHourInSec-startTimeInSec ;
                    peakTime = endTimeInSec-startHourInSec;
                }
                // cross the end of the peak
                else if (startHourInSec <= startTimeInSec && startTimeInSec <= endHourInSec && endHourInSec <= endTimeInSec ){
                    peakTime = endHourInSec - startTimeInSec;
                    offPeakTime = endTimeInSec - endHourInSec ;
                }
                // cross whole peak
                else if (sHour <= startHour && eHour >= endHour){
                    peakTime = endHourInSec-startHourInSec;
                    offPeakTime = (startHourInSec-startTimeInSec) + (endTimeInSec-endHourInSec);
                }

                duration[0] = offPeakTime;
                duration[1] = peakTime;
            }
        }

        return duration;
    }
}
