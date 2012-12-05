package tests;

import builders.*;
import com.acmetelecom.Call;
import com.acmetelecom.Cost;
import com.acmetelecom.LineItem;
import com.acmetelecom.customer.Customer;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 01:51
 * To change this template use File | Settings | File Templates.
 */

/*
Test for LineItemBuilder to see if it can build a correct LineItem
 */
public class LineItemBuilderTest {
    Customer cus1 = CustomerBuilder.aCustomer()
            .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
            .build();
    Customer cus2 = CustomerBuilder.aCustomer()
            .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
            .build();

    long startTime = new Long(1990*1000);
    long endTime = new Long(1991*1000);
    Cost cost = new Cost(new BigDecimal(99));
    Call call = CallBuilder.aCall().startAt(
            CallStartBuilder.aCallStart()
                    .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(startTime)
                    .build()

    ).endAt(
            CallEndBuilder.aCallEnd()
                    .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(endTime)
                    .build()
    ).build();

    LineItem lineItem = LineItemBuilder.aLineItem().withCall(call).withACostOf(cost.getCallCost()).build();

    @Test
    public void dateTest(){
        assertEquals(lineItem.date(),call.date());
    }

    @Test
    public void calleeTest(){
        assertEquals(lineItem.callee(), cus2.getPhoneNumber());
    }

    @Test
    public void durationMinutesTest(){
        assertEquals(lineItem.durationMinutes(), "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60));
    }

    @Test
    public void costTest(){
        assertEquals(lineItem.cost().getCallCost(),cost.getCallCost());
    }
}
