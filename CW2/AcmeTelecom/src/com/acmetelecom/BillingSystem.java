package com.acmetelecom;

import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.rate.PeakSeperateOffPeakRateEngine;
import com.acmetelecom.rate.ProfitableRateEngine;
import com.acmetelecom.rate.RateEngine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BillingSystem implements Biller {

    private List<CallEvent> callLog = new ArrayList<CallEvent>();
    private HashMap<String, Boolean> callInProgress = new HashMap<String, Boolean>();

    @Override
    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee));
        callInProgress.put(caller, Boolean.TRUE);
    }

    @Override
    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee));
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
            RateEngine rateEngine = new ProfitableRateEngine();
            cost = rateEngine.calculateCost(call ,tariff);

//            DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
//            if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime()) && call.durationSeconds() < 12 * 60 * 60) {
//                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
//            } else {
//                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
//            }

            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(call, callCost));
        }

        new BillGenerator().send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }
}
