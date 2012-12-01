package tests;

import Builders.*;
import com.acmetelecom.Call;
import com.acmetelecom.LineItem;
import com.acmetelecom.customer.Customer;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 29/11/2012
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

public class LineItemUnitTest {
    Customer cus1 = CustomerBuilder.aCustomer()
            .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
            .build();
    Customer cus2 = CustomerBuilder.aCustomer()
            .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
            .build();
    Call call = CallBuilder.aCall().startAt(
            CallStartBuilder.aCallStart()
                    .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1111))
                    .build()

    ).endAt(
            CallEndBuilder.aCallEnd()
                    .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2222))
                    .build()
    ).build();

    BigDecimal cost = new BigDecimal(1111);
    LineItem lineItem = LineItemBuilder.aLineItem().withCall(call).withACostOf(cost).build();

    @Test
    public void dateTest(){
        assertEquals(lineItem.date(), call.date());
    }

    @Test
    public void getCalleeTest(){
        assertEquals(lineItem.callee(),cus2.getPhoneNumber());
    }

    @Test
    public void durationMinutesTest(){
        assertEquals(lineItem.durationMinutes(), "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60));
    }

    @Test
    public void costTest(){
        assertEquals(lineItem.cost(),cost);
    }

}
