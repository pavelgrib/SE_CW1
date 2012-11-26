package fake;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-26
 * Time: 下午2:29
 */
public class FakeRunner {

    public static void main(String arges[]){
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          //PrintStream ps = new PrintStream(os);
          FakePrinter fakePrinter = new FakePrinter(System.out);
          FakeGenerator fakeGenerator = new FakeGenerator(fakePrinter);

          FakeBillingSystem bs = new FakeBillingSystem(fakeGenerator);

        bs.setTime(System.currentTimeMillis());
        bs.callInitiated("447722113434", "447766511332");
        bs.setTime(bs.getTime()+20000);
        bs.callCompleted("447722113434", "447766511332");

        bs.setTime(System.currentTimeMillis());
        bs.callInitiated("447722113434", "447711111111");
        bs.setTime(bs.getTime()+30000);
        bs.callCompleted("447722113434", "447711111111");

        bs.setTime(System.currentTimeMillis());
        bs.callInitiated("447777765432", "447711111111");
        bs.setTime(bs.getTime()+60000);
        bs.callCompleted("447777765432", "447711111111");

        bs.createCustomerBills();
    }
}
