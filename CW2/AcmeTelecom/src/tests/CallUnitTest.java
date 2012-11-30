package tests;

import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.acmetelecom.rate.DaytimePeakPeriod;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-30
 * Time: 11:21 AM
 */
public class CallUnitTest {

    public long time = 1354060800000l;  // set test time to 2012-11-28 0:00
    public CallStart callStart = new CallStart("Caller","Callee",time);
    public CallEnd callEnd = new CallEnd("Caller","Callee",(time+10*1000));
    public Call call = new Call(callStart,callEnd);

    @Test
    public void testGetCallee(){
        assertEquals("Callee",call.callee());
    }

    @Test
    public void testDurationSecond(){
        assertEquals(10,call.durationSeconds());
    }

    @Test
    public void testDate(){
        Date date = new Date(time);
        assertEquals(new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss").format(date), call.date());
    }

    @Test
    public void testStartTime(){
        assertEquals(new Date(time),call.startTime());
    }

    @Test
    public void testEndTime(){
        assertEquals(new Date(time+10*1000),call.endTime());
    }
}
