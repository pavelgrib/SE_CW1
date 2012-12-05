package com.acmetelecom;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */

/*
This interface contains four methods that a billing system would need
 */
public interface Biller {

    //used for call initiation
    void callInitiated(Caller caller, Callee callee);

    //used for call completion
    void callCompleted(Caller caller, Callee callee);

    //used for checking if a call is in progress
    boolean callInProgress(Caller caller);

    //create all customer bills and print it out in HTML
    void createCustomerBills();
}
