package tests;

import Builders.CallBuilder;
import Builders.CallEndBuilder;
import Builders.CallStartBuilder;
import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 01:34
 * To change this template use File | Settings | File Templates.
 */
public class CallBuilderTest {

    CallStart start = CallStartBuilder.aCallStart().withCaller("Alice").withCallee("Bob").withStartTime(new Long(1990*1000)).build();
    CallEnd end = CallEndBuilder.aCallEnd().withCaller("Alice").withCallee("Bob").withEndTime(new Long(1991*1000)).build();

    Call call = CallBuilder.aCall().startAt(start).endAt(end).build();

    @Test
    public void calleeTest(){
        assertEquals(call.callee(),start.getCallee());
    }

    @Test
    public void durationSecondTest(){
        assertEquals(call.durationSeconds(),1);
    }

    @Test
    public void dateTest(){

        Date date = new Date(new Long(1990*1000));
        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss").format(date), call.date());
    }

    @Test
    public void startTimeTest(){
        assertEquals(call.startTime(),new Date(start.time()));
    }

    @Test
    public void endTimeTest(){
        assertEquals(call.endTime(),new Date(end.time()));
    }
}
