package tests;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.Callee;
import com.acmetelecom.Caller;

public class Runner {
    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        BillingSystem billingSystem = new BillingSystem();
        Caller caller = new Caller("447722113434");
        Callee callee = new Callee("447766511332");
        billingSystem.callInitiated(caller, callee);
        sleepSeconds(20);
        billingSystem.callCompleted(caller, callee);

        billingSystem.callInitiated(caller, callee);
        sleepSeconds(30);
        billingSystem.callCompleted(caller, callee);

        billingSystem.callInitiated(caller, callee);
        sleepSeconds(60);
        billingSystem.callCompleted(caller, callee);

        billingSystem.createCustomerBills();
    }
    private static void sleepSeconds(int n) throws InterruptedException {
        Thread.sleep(n * 1000);
    }
}