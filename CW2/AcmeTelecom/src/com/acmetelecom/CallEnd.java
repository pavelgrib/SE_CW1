package com.acmetelecom;

public class CallEnd extends CallEvent {
    public CallEnd(Caller caller, Callee callee, long time) {
        super(caller, callee, time);
    }
}
