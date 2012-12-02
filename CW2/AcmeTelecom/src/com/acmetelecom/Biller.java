package com.acmetelecom;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Biller {
    void callInitiated(Caller caller, Callee callee);

    void callCompleted(Caller caller, Callee callee);

    boolean callInProgress(Caller caller);

    void createCustomerBills();
}
