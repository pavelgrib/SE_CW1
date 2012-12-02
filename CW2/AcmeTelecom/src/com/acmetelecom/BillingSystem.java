package com.acmetelecom;

import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.rate.PeakSeperateOffPeakRateEngine;

public class BillingSystem implements Biller {


    private BillingSystemLogical billingSystemLogical = null;

    public BillingSystem(){
        billingSystemLogical = new BillingSystemLogical(
                                                        CentralCustomerDatabase.getInstance()
                                                        ,CentralTariffDatabase.getInstance()
                                                        ,new BillGenerator(HtmlPrinter.getInstance())
                                                        ,new PeakSeperateOffPeakRateEngine()
        );

    }

    @Override
    public void callInitiated(Caller caller, Callee callee){
        billingSystemLogical.setTime((System.currentTimeMillis()));
        billingSystemLogical.callInitiated(caller, callee);
    }

    @Override
    public void callCompleted(Caller caller, Callee callee){
        billingSystemLogical.setTime(System.currentTimeMillis());
        billingSystemLogical.callCompleted(caller, callee);
    }

    @Override
    public boolean callInProgress(Caller caller){
        return billingSystemLogical.callInProgress(caller);
    }

    @Override
    public void createCustomerBills(){
        billingSystemLogical.createCustomerBills();
    }







}
