package tests;

import com.acmetelecom.Generator;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import java.util.List;

import com.acmetelecom.customer.TariffLibrary;
import fitlibrary.runner.CustomRunner;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JMock.class)
public class BillingSystemTest {

    Mockery context = new Mockery();
    CustomerDatabase customerDB = context.mock(CustomerDatabase.class);
    TariffLibrary tariffDB = context.mock(TariffLibrary.class);
    Generator billGen = context.mock(Generator.class);

    @Test
    public void BillsWereGeneratedTest() {
        List<Customer> customers = customerDB.getCustomers();

        context.checking(new Expectations() {{

        }});
    }
}
