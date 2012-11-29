package fake;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.rate.PeakSeperateOffPeakRateEngine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-26
 * Time: 下午2:29
 */
public class FakeRunner {

    public static void main(String arges[]) throws InterruptedException {
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          //PrintStream ps = new PrintStream(os);
          FakePrinter fakePrinter = new FakePrinter(System.out);
          FakeGenerator fakeGenerator = new FakeGenerator(fakePrinter);

          FakeBillingSystem bs = new FakeBillingSystem(fakeGenerator);

//        bs.setTime(System.currentTimeMillis());
//        bs.callInitiated("447722113434", "447766511332");
//        bs.setTime(bs.getTime()+20000);
//        bs.callCompleted("447722113434", "447766511332");
//
//        bs.setTime(System.currentTimeMillis());
//        bs.callInitiated("447722113434", "447711111111");
//        bs.setTime(bs.getTime()+30000);
//        bs.callCompleted("447722113434", "447711111111");
//
//        bs.setTime(System.currentTimeMillis());
//        bs.callInitiated("447777765432", "447711111111");
//        bs.setTime(bs.getTime()+60000);
//        bs.callCompleted("447777765432", "447711111111");
//
//        bs.createCustomerBills();

//        Date a = new Date();
//        Date b = new Date();
//        System.out.println(">>>>>>>>>>>>>>>>> "+a.after(b));
//
        long time = 1354086000000l; // 2012-11-28 7:00


        FakeCallStart fakeCallStart = new FakeCallStart("1","2",(time-3600000));
        FakeCallEnd fakeCallEnd = new FakeCallEnd("1","2", (time+3600000+12*3600000));
        Call call = new Call(fakeCallStart,fakeCallEnd);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(call.startTime());
        Date startDate = calendar.getTime();
        int sHour = calendar.get(Calendar.HOUR_OF_DAY);
        int sMinute = calendar.get(Calendar.MINUTE);
        int sSecond = calendar.get(Calendar.SECOND);

        calendar.setTime(call.endTime());
        Date endDate = calendar.getTime();
        int eHour = calendar.get(Calendar.HOUR_OF_DAY);
        int eMinute = calendar.get(Calendar.MINUTE);
        int eSecond = calendar.get(Calendar.SECOND);

        System.out.println(">>>>>>>>"+sHour+"  "+sMinute+"  "+sSecond);
        System.out.println(">>>>>>>>"+eHour+"  "+eMinute+"  "+eSecond);
        PeakSeperateOffPeakRateEngine engine = new PeakSeperateOffPeakRateEngine();
        engine.calculateCost(call);


//
//        System.out.println(System.currentTimeMillis());
//        Thread.sleep(10 * 1000);
//        System.out.println(System.currentTimeMillis());

    }
}
