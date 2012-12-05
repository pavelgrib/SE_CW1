package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rate.RateEngine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 28/11/2012
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */

/*
The calculation or logical part of Billing System
The dependencies in constructor are passed from BillingSystem.class
 */
public class BillingSystemLogical implements Biller{

    private List<CallEvent> callLog = new ArrayList<CallEvent>();
    private HashMap<String, Boolean> callInProgress = new HashMap<String, Boolean>();
    private long time;

    private Generator generator = null;
    private RateEngine rateEngine = null;
    private TariffLibrary tarrifLibrary = null;
    private CustomerDatabase customerDatabase = null;

    public BillingSystemLogical(CustomerDatabase customerDatabase, TariffLibrary tariffLibrary, Generator generator,
                                RateEngine rateEngine) {
        this.tarrifLibrary = tariffLibrary;
        this.customerDatabase = customerDatabase;
        this.generator=generator;
        this.rateEngine=rateEngine;
    }

    // facilitate the FIT test
    public long getTime(){
        return time;
    }

    // facilitate the FIT test
    public void setTime(long time){
        this.time = time;
    }

    @Override
    public void callInitiated(Caller caller, Callee callee) {
        callLog.add(new CallStart(caller, callee,time));
        callInProgress.put(caller.toString(), Boolean.TRUE);
    }

    @Override
    public void callCompleted(Caller caller, Callee callee) {
        callLog.add(new CallEnd(caller, callee,time));
        callInProgress.put(caller.toString(), Boolean.FALSE);
    }

    @Override
    public boolean callInProgress(Caller caller) {
        return callInProgress.get(caller.toString());
    }

    @Override
    public void createCustomerBills() {
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            createBillFor(customer);
        }
        callLog.clear();
    }

    private void createBillFor(Customer customer) {
        List<CallEvent> customerEvents = new ArrayList<CallEvent>();
        for (CallEvent callEvent : callLog) {
            if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
                customerEvents.add(callEvent);
            }
        }

        List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : customerEvents) {
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }

        Cost totalBill = new Cost(BigDecimal.ZERO);
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            Tariff tariff = tarrifLibrary.tarriffFor(customer);

            Cost cost;
            cost = rateEngine.calculateCost(call ,tariff);


            Cost callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(callCost, call));
        }

        generator.send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }

}
