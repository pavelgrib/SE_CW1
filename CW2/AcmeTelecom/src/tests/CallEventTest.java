package tests;

import com.acmetelecom.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallEventTest {

    private Caller caller = new Caller("1");
    private Callee callee = new Callee("2");

    @Test
    public void CallStartTest() {
        long startTime = 1353783300;
        CallEvent startEvent = new CallStart(caller, callee, startTime);
        assertEquals(startEvent.time(), startTime);
    }

    @Test
    public void CallEndTest() {
        long endTime = 1353783900;
        CallEvent endEvent = new CallStart(caller, callee, endTime);
        assertEquals(endEvent.time(),  endTime);
    }

    @Test
    public void CalleeAndCallerTest() {
        CallEvent startEvent = new CallStart(caller, callee,System.currentTimeMillis());
        assertEquals(startEvent.getCallee(), callee.toString());
        assertEquals(startEvent.getCaller(), caller.toString());

        CallEvent endEvent = new CallEnd(caller, callee,System.currentTimeMillis());
        assertEquals(endEvent.getCallee(), callee.toString());
        assertEquals(endEvent.getCaller(), caller.toString());
    }
}
