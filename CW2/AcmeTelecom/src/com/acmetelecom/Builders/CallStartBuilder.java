package com.acmetelecom.Builders;

import com.acmetelecom.CallStart;
import com.sun.istack.internal.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class CallStartBuilder implements Builder {
    private String caller;
    private String callee;
    private long time;

    public static CallStartBuilder aCallStart(){
        return new CallStartBuilder();
    }
    private CallStartBuilder(){
        this.caller = null;
        this.callee = null;
        this.time = 0;
    }

    public CallStartBuilder withCaller(String caller){
        this.caller = caller;
        return this;
    }
    public CallStartBuilder withCallee(String callee){
        this.callee = callee;
        return this;
    }
    public CallStartBuilder withStartTime(long time){
        this.time = time;
        return this;
    }

    public CallStart build(){
        return new CallStart(caller,callee,time);
    }
}
