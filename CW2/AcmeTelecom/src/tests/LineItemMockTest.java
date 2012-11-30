package tests;

import com.acmetelecom.Call;
import com.acmetelecom.LineItem;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 29/11/2012
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

@RunWith(JMock.class)
public class LineItemMockTest {

    private static org.hamcrest.Matcher<String> anyString() {
        return new org.hamcrest.core.IsAnything<String>();
    }

    private static Matcher<Integer> anyInt(){
        return new IsAnything<Integer>();
    }

    private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    Call call = context.mock(Call.class);
    BigDecimal cost = context.mock(BigDecimal.class);
    LineItem lineItem = new LineItem(call, cost);

    @Test
    public void dateTest(){
        context.checking(new Expectations(){{
            oneOf(call).date();will(returnValue(with(anyString())));
        }});

        lineItem.date();
    }

    @Test
    public void getCalleeTest(){
        context.checking(new Expectations(){{
            oneOf(call).callee();will(returnValue(with(anyString())));
        }});
        lineItem.callee();
    }

    @Test
    public void durationMinutesTest(){
        context.checking(new Expectations(){{
            allowing(call).durationSeconds();will(returnValue(with(anyInt())));
        }});
       lineItem.durationMinutes();
    }

}
