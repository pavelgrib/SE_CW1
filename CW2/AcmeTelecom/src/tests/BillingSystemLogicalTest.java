package tests;

import com.acmetelecom.BillingSystemLogical;
import com.acmetelecom.Generator;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rate.RateEngine;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 29/11/2012
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JMock.class)
public class BillingSystemLogicalTest {

    Mockery context = new Mockery();
    CustomerDatabase customerDB = context.mock(CustomerDatabase.class);
    TariffLibrary tariffDB = context.mock(TariffLibrary.class);
    Generator generator = context.mock(Generator.class);
    RateEngine rateEngine = context.mock(RateEngine.class);

    BillingSystemLogical billingSystemLogical = new BillingSystemLogical(customerDB, tariffDB, generator, rateEngine);


    @Test
    public void testTimeSetterAndGetter() {
        long time = 111111;
        billingSystemLogical.setTime(time);
        assertEquals(billingSystemLogical.getTime(), time);
    }

    @Test
    public void testCallingProgress(){
        billingSystemLogical.callInitiated("123123123123", "321321321321");
        assertEquals(billingSystemLogical.callInProgress("123123123123"), true);
        billingSystemLogical.callCompleted("123123123123", "321321321321");
        assertEquals(billingSystemLogical.callInProgress("123123123123"), false);
    }


}
