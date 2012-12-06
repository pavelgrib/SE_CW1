package tests;

import builders.CustomerBuilder;
import com.acmetelecom.Transaction;
import com.acmetelecom.customer.*;
import com.acmetelecom.rate.ProfitableRateEngine;
import com.sun.tools.javac.util.List;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 12/6/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JMock.class)
public class RateEngineMockTest {


    Mockery context = new Mockery();
    Transaction call = context.mock(Transaction.class);
    TariffLibrary tl = context.mock(TariffLibrary.class);
    Customer c = CustomerBuilder.aCustomer()
            .withFullName("abc").withPhoneNumber("123").ofPricePlane("pp")
            .build();

    @Test
    public void costCalculated() {


        final Date startDate = call.startTime();
        final Date endDate = call.endTime();
        final int durationSec = call.durationSeconds();
        Tariff tariff = tl.tarriffFor(c);
        ProfitableRateEngine pre = new ProfitableRateEngine(7, 19);
        context.checking(new Expectations() {{
            exactly(1).of(call).startTime();
            will(returnValue(startDate));
            exactly(1).of(call).durationSeconds();
            will(returnValue(durationSec));
            exactly(1).of(call).endTime();
            will(returnValue(endDate));
        }});
        pre.calculateCost(call, tariff);
    }
}
