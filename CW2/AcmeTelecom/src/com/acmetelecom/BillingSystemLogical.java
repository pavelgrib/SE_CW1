package com.acmetelecom;

import com.acmetelecom.customer.*;
import com.acmetelecom.rate.RateEngine;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee,time));
        callInProgress.put(caller, Boolean.TRUE);
    }

    @Override
    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee,time));
        callInProgress.put(caller, Boolean.FALSE);
    }

    @Override
    public boolean callInProgress(String caller) {
        return callInProgress.get(caller);
    }

    @Override
    public void createCustomerBills() {
        List<Customer> customers = CentralCustomerDatabase.getInstance().getCustomers();
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

        BigDecimal totalBill = new BigDecimal(0);
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            Tariff tariff = CentralTariffDatabase.getInstance().tarriffFor(customer);

            BigDecimal cost;
            cost = rateEngine.calculateCost(call ,tariff);

            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(call, callCost));
        }

        new BillGenerator().send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }

}
