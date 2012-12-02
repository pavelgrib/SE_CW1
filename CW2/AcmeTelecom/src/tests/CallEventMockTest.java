package tests;

import com.acmetelecom.Biller;

import com.acmetelecom.Callee;
import com.acmetelecom.Caller;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
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
public class CallEventMockTest extends TestCase {


    Caller caller = new Caller("1");
    Callee callee = new Callee("2");

    Mockery context = new Mockery();
    Biller bs = context.mock(Biller.class);
    @Test
    public void TestCallStarted() {


        context.checking(new Expectations() {{
            allowing(bs).callInitiated(caller, callee);
            allowing(bs).callInProgress(caller);
            will(returnValue(Boolean.TRUE));
        }});

        bs.callInitiated(caller, callee);
    }

    @Test
    public void TestCallEnded() throws Exception {
        context.checking(new Expectations() {{
            allowing(bs).callInitiated(caller, callee);
        }});

        bs.callInitiated(caller, callee);
        Thread.sleep(10 * 1000);

        context.checking( new Expectations() {{
            exactly(1).of(bs).callCompleted(caller, callee);
            allowing(bs).callInProgress(caller);
            will(returnValue(Boolean.FALSE));
        }});
        bs.callCompleted(caller, callee);
    }
}
