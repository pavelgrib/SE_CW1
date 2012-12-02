package tests;

import Builders.CallBuilder;
import Builders.CallEndBuilder;
import Builders.CallStartBuilder;
import Builders.CustomerBuilder;
import com.acmetelecom.Call;
import com.acmetelecom.customer.Customer;
import fake.FakePrinter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class FakePrinterTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    FakePrinter printer = new FakePrinter(new PrintStream(this.out));


    @Test
    public void printHeadingTest(){
        Customer cus1 = CustomerBuilder.aCustomer()
                .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
                .build();
        Customer cus2 = CustomerBuilder.aCustomer()
                .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
                .build();
        Call call = CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1000))
                        .build()
        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2000))
                        .build()
        ).build();

        String cost = "99.99";

        printer.printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());
        printer.printItem(call.startTime().toString(), cus2.getPhoneNumber(), Integer.toString(call.durationSeconds()), cost);
        printer.printTotal(cost);

        assertTrue(this.out.toString().contains(cus1.getFullName()+" - "+cus1.getPhoneNumber()+" - "+cus1.getPricePlan()+"\n")
                && this.out.toString().contains(call.startTime().toString()+" - Call:"+ cus2.getPhoneNumber()+" - Duration:"+Integer.toString(call.durationSeconds())+" - Cost:"+cost+"\n")
                && this.out.toString().contains("Total: "+cost+"\n")) ;
    }
}
