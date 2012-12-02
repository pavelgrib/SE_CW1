package com.acmetelecom;

public abstract class CallEvent {
    private Caller caller;
    private Callee callee;
    private long time;

    public CallEvent(Caller caller, Callee callee, long timeStamp) {
        this.caller = caller;
        this.callee = callee;
        this.time = timeStamp;
    }

    public String getCaller() {
    return caller.toString();
}

    public String getCallee() {
        return callee.toString();
    }

    public long time() {
        return time;
    }
}
