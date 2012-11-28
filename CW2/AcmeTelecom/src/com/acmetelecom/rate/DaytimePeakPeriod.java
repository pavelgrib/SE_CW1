package com.acmetelecom.rate;

import java.util.Calendar;
import java.util.Date;

public class DaytimePeakPeriod {
    private int start;
    private int end;

    public DaytimePeakPeriod(){
        new DaytimePeakPeriod(7,19);
    }


    public DaytimePeakPeriod(int start,int end){
        this.start = start;
        this.end = end;
    }

    public boolean offPeak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < this.start || hour >= this.end;
    }
}
