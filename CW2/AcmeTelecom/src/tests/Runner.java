package tests;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.Callee;
import com.acmetelecom.Caller;
import org.junit.Test;

public class Runner {

    @Test
    public void billingSystemTest() throws Exception{
        System.out.println("Running...");
        BillingSystem billingSystem = new BillingSystem();
        Caller caller = new Caller("447722113434");
        Callee callee = new Callee("447766511332");
        billingSystem.callInitiated(caller, callee);
        sleepSeconds(1);
        assert(billingSystem.callInProgress(caller)==true);
        billingSystem.callCompleted(caller, callee);

        billingSystem.callInitiated(caller, callee);
        sleepSeconds(1);
        assert(billingSystem.callInProgress(caller)==true);
        billingSystem.callCompleted(caller, callee);

        billingSystem.callInitiated(caller, callee);
        sleepSeconds(1);
        assert(billingSystem.callInProgress(caller)==true);
        billingSystem.callCompleted(caller, callee);

        billingSystem.createCustomerBills();

    }
    private static void sleepSeconds(int n) throws InterruptedException {
        Thread.sleep(n * 10);
    }
}