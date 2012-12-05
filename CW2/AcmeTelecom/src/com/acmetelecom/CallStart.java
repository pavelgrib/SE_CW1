package com.acmetelecom;

/*
refactored in constructor so that we can give a specific start time
 */
public class CallStart extends CallEvent {
    public CallStart(Caller caller, Callee callee, long time) {
        super(caller, callee, time);
    }


}
