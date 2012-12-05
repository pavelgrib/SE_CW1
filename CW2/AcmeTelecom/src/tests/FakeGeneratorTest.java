package tests;

import builders.*;
import com.acmetelecom.Call;
import com.acmetelecom.LineItem;
import com.acmetelecom.Printer;
import com.acmetelecom.customer.Customer;
import fake.FakeGenerator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */

/*
This is the unit test for FakeGenerator class
 */
public class FakeGeneratorTest {
    public org.hamcrest.Matcher<String> anyString() {
        return new org.hamcrest.core.IsAnything<String>();
    }

//    private Mockery context = new JUnit4Mockery() {{
//        setImposteriser(ClassImposteriser.INSTANCE);
//    }};

    private Mockery context = new Mockery();


    Customer cus1 = CustomerBuilder.aCustomer()
            .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
            .build();
    Customer cus2 = CustomerBuilder.aCustomer()
            .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
            .build();

    @Test
    public void sendWithOneCallTest(){

        Call call = CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1111))
                        .build()

        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2222))
                        .build()
        ).build();

        LineItem lineItem = LineItemBuilder.aLineItem().withCall(call).withACostOf(new BigDecimal(1111)).build();

        List<LineItem> lineItems = new ArrayList<LineItem>();

        lineItems.add(lineItem);

        final String totalBill = "some random bill";

        final Printer printer = context.mock(Printer.class);
        FakeGenerator generator= new FakeGenerator(printer);

        context.checking(new Expectations(){{
            oneOf(printer).printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());

            exactly(1).of(printer).printItem(with(anyString()), with(anyString()), with(anyString()), with(anyString()));

            oneOf(printer).printTotal(totalBill);
        }});
        generator.send(cus1,lineItems,totalBill);
    }

    @Test
    public void sendWithMultipleCallsTest(){

        Call call1 =CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1111))
                        .build()

        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2222))
                        .build()
        ).build();

        Call call2 =CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(3333))
                        .build()

        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(4444))
                        .build()
        ).build();

        LineItem lineItem1 = LineItemBuilder.aLineItem().withCall(call1).withACostOf(new BigDecimal(1111)).build();
        LineItem lineItem2 = LineItemBuilder.aLineItem().withCall(call2).withACostOf(new BigDecimal(1111)).build();

        List<LineItem> lineItems = new ArrayList<LineItem>();

        lineItems.add(lineItem1);
        lineItems.add(lineItem2);

        final String totalBill = "some random bill";

        final Printer printer = context.mock(Printer.class);
        FakeGenerator generator= new FakeGenerator(printer);

        context.checking(new Expectations(){{
            oneOf(printer).printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());

            exactly(2).of(printer).printItem(with(anyString()),with(anyString()),with(anyString()),with(anyString()));

            oneOf(printer).printTotal(totalBill);
        }});
        generator.send(cus1,lineItems,totalBill);
    }

}
