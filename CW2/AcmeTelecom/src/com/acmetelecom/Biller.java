package com.acmetelecom;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Biller {
    void callInitiated(String caller, String callee);

    void callCompleted(String caller, String callee);

    boolean callInProgress(String caller);
    void createCustomerBills();
}
