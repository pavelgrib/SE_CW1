package tests;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.Callee;
import com.acmetelecom.Caller;

/**
 * Created with IntelliJ IDEA.
 * User: paul
 * Date: 04/12/2012
 * Time: 00:55
 * To change this template use File | Settings | File Templates.
 */
public class PerformanceRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        long start = System.nanoTime();
        BillingSystem billingSystem = new BillingSystem();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("took " + elapsedTime / 10e9 + " seconds to start BillingSystem");

        start = System.nanoTime();
        for ( int i=0; i < 100000; ++i ) {
            Caller caller = new Caller("447722113434");
            Callee callee = new Callee("447766511332");
            billingSystem.callInitiated(caller, callee);
            Thread.sleep(1);
            billingSystem.callCompleted(caller, callee);
        }
        elapsedTime = System.nanoTime() - start;
        System.out.println("took " + elapsedTime / 1e9 + " seconds to add 100000 calls");

//        start = System.nanoTime();
//        billingSystem.createCustomerBills();
//        elapsedTime = System.nanoTime() - start;
//        System.out.println("took " + elapsedTime + " nanseconds to create bills");
    }
    private static void sleepSeconds(int n) throws InterruptedException {
        Thread.sleep(n * 1000);
    }

}
