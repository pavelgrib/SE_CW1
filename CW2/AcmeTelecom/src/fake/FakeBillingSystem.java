package fake;

import com.acmetelecom.*;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.rate.DaytimePeakPeriod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-26
 * Time: 下午12:59
 */
public class FakeBillingSystem implements Biller{

    private Generator generator = null;
    private long time;
    private List<CallEvent> callLog = new ArrayList<CallEvent>();
    private HashMap<String, Boolean> callInProgress = new HashMap<String, Boolean>();

    public FakeBillingSystem(Generator generator){
        this.generator = generator;
        time = System.currentTimeMillis();
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }

    @Override
    public void callInitiated(String caller, String callee) {
        callLog.add(new FakeCallStart(caller,callee,time));
        callInProgress.put(caller, Boolean.TRUE);
    }

    @Override
    public void callCompleted(String caller, String callee) {
        callLog.add(new FakeCallEnd(caller,callee,time));
        callInProgress.put(caller, Boolean.FALSE);
    }

    @Override
    public boolean callInProgress(String caller) {
        return callInProgress.get(caller);
    }

    @Override
    public void createCustomerBills() {
        // TODO different customer database
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
            if (event instanceof FakeCallStart) {
                start = event;
            }
            if (event instanceof FakeCallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }
        //System.out.println("Calls ==========================="+calls.size());
        BigDecimal totalBill = new BigDecimal(0);
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {
            //TODO Different Tariff plan
            Tariff tariff = CentralTariffDatabase.getInstance().tarriffFor(customer);

            BigDecimal cost;
            //System.out.println("DURATION= "+call.durationSeconds());
            DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
            if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime()) && call.durationSeconds() < 12 * 60 * 60) {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
            } else {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
            }

            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(call, callCost));
        }

        generator.send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }
}
