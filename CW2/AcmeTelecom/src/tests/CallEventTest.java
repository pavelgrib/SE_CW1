package tests;

import com.acmetelecom.CallEnd;
import com.acmetelecom.CallEvent;
import com.acmetelecom.CallStart;
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

    private String caller = "1";
    private String callee = "2";

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
        assertEquals(startEvent.getCallee(), callee);
        assertEquals(startEvent.getCaller(), caller);

        CallEvent endEvent = new CallEnd(caller, callee,System.currentTimeMillis());
        assertEquals(endEvent.getCallee(), callee);
        assertEquals(endEvent.getCaller(), caller);
    }
}
