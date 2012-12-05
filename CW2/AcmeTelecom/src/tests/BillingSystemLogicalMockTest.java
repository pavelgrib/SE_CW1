package tests;

import com.acmetelecom.*;
import builders.CustomerBuilder;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rate.RateEngine;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 11/24/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JMock.class)
public class BillingSystemLogicalMockTest {

    // Matcher for an iterable collection as a specific size
    private static org.hamcrest.Matcher<Iterable<LineItem>> iterableWithSize(int size) {
        return org.hamcrest.collection.IsIterableWithSize.<LineItem>iterableWithSize(size);
    }

    // Matcher for any string
    private static org.hamcrest.Matcher<String> anyString() {
        return new org.hamcrest.core.IsAnything<String>();
    }

    // Matcher for any call
    private static org.hamcrest.Matcher<Call> anyCall() {
        return new org.hamcrest.core.IsAnything<Call>();
    }

    // Matcher for any tariff
    private  static Matcher<Tariff> anyTariff() {
        return new IsAnything<Tariff>();
    }



    Mockery context = new Mockery();
    CustomerDatabase customerDB = context.mock(CustomerDatabase.class);
    TariffLibrary tariffDB = context.mock(TariffLibrary.class);
    Generator generator = context.mock(Generator.class);
    RateEngine rateEngine = context.mock(RateEngine.class);

    BillingSystemLogical bilLingSystemLogical = new BillingSystemLogical(customerDB, tariffDB, generator, rateEngine);

    Customer cus1 = CustomerBuilder.aCustomer()
            .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
            .build();
    Customer cus2 = CustomerBuilder.aCustomer()
            .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
            .build();




    @Test
    //test of create bills for all customers
    //where there is only one customer record in database
    //and the user makes no call
    public void createAllCustomersBillsOneCusNoCallEach(){
        final List<Customer> customerList = new LinkedList<Customer>();
        customerList.add(cus1);

        context.checking(new Expectations() {{
            allowing(customerDB).getCustomers();will(returnValue(customerList));
            oneOf(generator).send(
                    with(equalTo(cus1)),
                    with(iterableWithSize(0)),
                    with(anyString()));
        }});

        bilLingSystemLogical.createCustomerBills();
    }

    @Test
    //test of creating bills for all customers
    //where there are two customer records in database
    //and only one of them makes one call to the other
    public void createAllCustomerBillsTwoCusOneCallOnly(){
        final List<Customer> customerList = new LinkedList<Customer>();
        final Cost costForCus1 = new Cost(new BigDecimal(10));
        customerList.add(cus1);
        customerList.add(cus2);

        context.checking(new Expectations() {{
            allowing(customerDB).getCustomers();will(returnValue(customerList));
            oneOf(tariffDB).tarriffFor(cus1);will(returnValue(Tariff.Business));

            oneOf(rateEngine).calculateCost(with(anyCall()), with(anyTariff())); will(returnValue(costForCus1));

            oneOf(generator).send(
                    with(equalTo(cus1)),
                    with(iterableWithSize(1)),
                    with(anyString())
            );

            oneOf(generator).send(
                    with(equalTo(cus2)),
                    with(iterableWithSize(0)),
                    with(anyString())
            );
        }});
        long time=bilLingSystemLogical.getTime();
        Caller caller = new Caller(cus1.getPhoneNumber());
        Callee callee = new Callee(cus2.getPhoneNumber());
        bilLingSystemLogical.callInitiated(caller, callee);
        bilLingSystemLogical.setTime(time + 10000);     //call lasts 10 sec
        bilLingSystemLogical.callCompleted(caller, callee);
        bilLingSystemLogical.createCustomerBills();
    }

    public void createAllCustomerBillsWithTwoCusOneCallEach(){
        final List<Customer> customerList = new LinkedList<Customer>();
        final Cost costForCus1 = new Cost(new BigDecimal(10));
        final Cost costForCus2 = new Cost(new BigDecimal(10));

        customerList.add(cus1);
        customerList.add(cus2);

        context.checking(new Expectations(){{
            allowing(customerDB).getCustomers();will(returnValue(customerList));
            oneOf(tariffDB).tarriffFor(cus1);will(returnValue(Tariff.Business));
            oneOf(tariffDB).tarriffFor(cus2);will(returnValue(Tariff.Business));

            oneOf(rateEngine).calculateCost(with(anyCall()), with(anyTariff())); will(returnValue(costForCus1));
            oneOf(rateEngine).calculateCost(with(anyCall()), with(anyTariff())); will(returnValue(costForCus2));

            oneOf(generator).send(
                    with(equalTo(cus1)),
                    with(iterableWithSize(1)),
                    with(anyString())
            );

            oneOf(generator).send(
                    with(equalTo(cus2)),
                    with(iterableWithSize(1)),
                    with(anyString())
            ) ;
        }});

        long time=bilLingSystemLogical.getTime();
        Caller caller = new Caller(cus1.getPhoneNumber());
        Callee callee = new Callee(cus2.getPhoneNumber());
        bilLingSystemLogical.callInitiated(caller, callee);
        bilLingSystemLogical.setTime(time+1000); // customer 1 called 10 sec
        bilLingSystemLogical.callCompleted(caller, callee);
        bilLingSystemLogical.callInitiated(caller, callee);
        bilLingSystemLogical.setTime(time+2000);// customer 2 called 10 sec
        bilLingSystemLogical.callCompleted(caller, callee);
        bilLingSystemLogical.createCustomerBills();
    }

    public void createAllCustomerBillsWithTwoCusAndOnlyTwoCallsFromOneOfThem(){
        final List<Customer> customerList = new LinkedList<Customer>();
        final Cost costForCus1Call1 = new Cost(new BigDecimal(10));
        final Cost costForCus1Call2 = new Cost(new BigDecimal(10));

        customerList.add(cus1);
        customerList.add(cus2);

        context.checking(new Expectations(){{
            allowing(customerDB).getCustomers();will(returnValue(customerList));
            oneOf(tariffDB).tarriffFor(cus1);will(returnValue(Tariff.Business));
            oneOf(tariffDB).tarriffFor(cus1);will(returnValue(Tariff.Business));

            oneOf(rateEngine).calculateCost(with(anyCall()), with(anyTariff())); will(returnValue(costForCus1Call1));
            oneOf(rateEngine).calculateCost(with(anyCall()), with(anyTariff())); will(returnValue(costForCus1Call2));

            oneOf(generator).send(
                    with(equalTo(cus1)),
                    with(iterableWithSize(2)),
                    with(anyString())
            );

            oneOf(generator).send(
                    with(equalTo(cus2)),
                    with(iterableWithSize(0)),
                    with(anyString())
            );
        }});

        long time=bilLingSystemLogical.getTime();
        Caller caller = new Caller(cus1.getPhoneNumber());
        Callee callee = new Callee(cus2.getPhoneNumber());
        bilLingSystemLogical.callInitiated(caller, callee);
        bilLingSystemLogical.setTime(time+1000); // customer 1 called 10 sec
        bilLingSystemLogical.callCompleted(caller,callee);
        bilLingSystemLogical.callInitiated(caller, callee);
        bilLingSystemLogical.setTime(time+2000);// customer 1 called 10 sec again
        bilLingSystemLogical.callCompleted(caller, callee);
        bilLingSystemLogical.createCustomerBills();
    }

    @Test
    public void testTimeSetterAndGetter() {
        long time = 111111;
        bilLingSystemLogical.setTime(time);
        assertEquals(bilLingSystemLogical.getTime(), time);
    }

    @Test
    public void testCallingProgress(){
        bilLingSystemLogical.callInitiated(new Caller("123123123123"), new Callee("321321321321"));
        assertEquals(bilLingSystemLogical.callInProgress(new Caller("123123123123")), true);
        bilLingSystemLogical.callCompleted(new Caller("123123123123"), new Callee("321321321321"));
        assertEquals(bilLingSystemLogical.callInProgress(new Caller("123123123123")), false);
    }
}
