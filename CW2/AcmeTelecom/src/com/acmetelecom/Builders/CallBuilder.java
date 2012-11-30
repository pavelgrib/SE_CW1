package com.acmetelecom.Builders;

import com.acmetelecom.Call;
import com.acmetelecom.CallEvent;
import com.sun.istack.internal.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class CallBuilder implements Builder {
    private CallEvent start;
    private CallEvent end;

    public static CallBuilder aCall(){
        return new CallBuilder();
    }

    public CallBuilder(){
        this.start=null;
        this.end=null;
    }

    public CallBuilder startAt(CallEvent start){
        this.start = start;
        return this;
    }

    public CallBuilder endAt(CallEvent end){
        this.end=end;
        return this;
    }

    @Override
    public Call build() {
        return new Call(start,end);
    }
}
