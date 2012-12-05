package com.acmetelecom;

/*
refactored in constructor so that we can give a specific end time
 */
public class CallEnd extends CallEvent {
    public CallEnd(Caller caller, Callee callee, long time) {
        super(caller, callee, time);
    }
}
