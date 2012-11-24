package tests;

import com.acmetelecom.Biller;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/21/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JMock.class)
public class CallEventMockTest {

    String caller = "1";
    String callee = "2";
    Mockery context = new Mockery();
    Biller bs = context.mock(Biller.class);
    @Test
    public void TestCallStarted() {


        context.checking(new Expectations() {{
            allowing(bs).callInProgress(caller);
            will(returnValue(Boolean.FALSE));
        }});

        bs.callInitiated(caller, callee);
    }

    @Test
    public void TestCallEnded() throws Exception {
        bs.callInitiated(caller, callee);

        context.checking( new Expectations() {{
            allowing(bs).callInProgress(caller);
            will(returnValue(Boolean.FALSE));
        }});

        Thread.sleep(10 * 1000);
        bs.callCompleted(caller, callee);
    }
}
