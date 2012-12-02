package com.acmetelecom;

public class CallStart extends CallEvent {
    public CallStart(Caller caller, Callee callee, long time) {
        super(caller, callee, time);
    }


}
