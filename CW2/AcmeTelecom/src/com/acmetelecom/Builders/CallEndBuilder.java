package com.acmetelecom.Builders;

import com.acmetelecom.CallEnd;
import com.sun.istack.internal.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class CallEndBuilder implements Builder {
    private String caller;
    private String callee;
    private long time;

    public static CallEndBuilder aCallEnd(){
        return new CallEndBuilder();
    }
    private CallEndBuilder(){
        this.caller = null;
        this.callee = null;
        this.time = 0;
    }

    public CallEndBuilder withCaller(String caller){
        this.caller = caller;
        return this;
    }
    public CallEndBuilder withCallee(String callee){
        this.callee = callee;
        return this;
    }
    public CallEndBuilder withEndTime(long time){
        this.time = time;
        return this;
    }

    public CallEnd build(){
        return new CallEnd(caller,callee,time);
    }
}
