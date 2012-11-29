package tests;

import com.acmetelecom.BillingSystemLogical;
import com.acmetelecom.Call;
import com.acmetelecom.Generator;
import com.acmetelecom.LineItem;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rate.RateEngine;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 11/24/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JMock.class)
public class LogicalBillingSystemTest {

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


    Mockery context = new Mockery();
    CustomerDatabase customerDB = context.mock(CustomerDatabase.class);
    TariffLibrary tariffDB = context.mock(TariffLibrary.class);
    Generator generator = context.mock(Generator.class);
    RateEngine rateEngine = context.mock(RateEngine.class);

    BillingSystemLogical bilLingSystemLogical = new BillingSystemLogical(customerDB, tariffDB, generator, rateEngine);

    Customer cus1 = new Customer("Alice","123456789","");
    Customer cus2 = new Customer("Bob","987654321","");
    Customer cus3 = new Customer("Charley", "111111111", "");

    @Test
    public void createAllCustomersBills() {
        final List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(cus1);
        customerList.add(cus2);

        context.checking(new Expectations() {{
            allowing(customerDB.getCustomers()).equals(customerList);
            oneOf(generator).send(with(equal(cus1)),

        }});
        .createCustomerBills();
    }
}
