package com.acmetelecom;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Call implements Transaction {
    private CallEvent start;
    private CallEvent end;

    public Call(CallEvent start, CallEvent end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String callee() {
        return start.getCallee();
    }

    @Override
    public int durationSeconds() {
        return (int) (((end.time() - start.time()) / 1000));
    }

    @Override
    public String date() {
        return new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss").format(new Date(start.time()));
    }

    @Override
    public Date startTime() {
        return new Date(start.time());
    }

    @Override
    public Date endTime() {
        return new Date(end.time());
    }
}
