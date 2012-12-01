package tests;

import Builders.CallEndBuilder;
import Builders.CallStartBuilder;
import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertSame;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */

public class CallTest {
    CallStart start = CallStartBuilder.aCallStart().withCaller("Alice").withCallee("Bob").withStartTime(new Long(1990*1000)).build();
    CallEnd end = CallEndBuilder.aCallEnd().withCaller("Alice").withCallee("Bob").withEndTime(new Long(1991*1000)).build();

    Call call = new Call(start,end);

    @Test
    public void calleeTest(){
        assertSame(call.callee(), start.getCallee());
    }

    @Test
    public void durationSecondsTest(){
        assertEquals(call.durationSeconds(),1);
    }

    @Test
    public void dateTest(){
        assertTrue(SimpleDateFormat.getInstance().format(new Date(start.time())).equals("1/1/70 1:33 AM"));
    }

    @Test
    public void startTimeTest(){
        assertTrue((new Date(start.time())).toString().equals("Thu Jan 01 01:33:10 GMT 1970"));
    }
    @Test
    public void endTimeTest(){
        assertTrue((new Date(end.time())).toString().equals("Thu Jan 01 01:33:11 GMT 1970"));
    }
}
