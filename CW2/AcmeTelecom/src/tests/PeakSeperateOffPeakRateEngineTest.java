package tests;

import com.acmetelecom.*;
import com.acmetelecom.rate.PeakSeperateOffPeakRateEngine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-29
 * Time: 下午1:37
 */
public class PeakSeperateOffPeakRateEngineTest {

    public long baseTime = 1354060800000l; // set a base time 2012-11-28 0:00
    public long oneHour = 3600000l;
    public long oneDay = oneHour * 24;
    PeakSeperateOffPeakRateEngine engine = new PeakSeperateOffPeakRateEngine();

    CallStart fakeCallStart; // fake call start
    CallEnd fakeCallEnd; // fake call end
    Call call;

    private Call setupCall(int fromHour, int toHour) {
        Caller caller = new Caller("1");
        Callee callee = new Callee("1");
        fakeCallStart = new CallStart(caller, callee, (baseTime + oneHour * fromHour));
        fakeCallEnd = new CallEnd(caller, callee, (baseTime + oneHour * toHour));
        return new Call(fakeCallStart, fakeCallEnd);

    }
    @Test
    public void testInOneDayAllInPeak() {
        // call was make from 8:00 to 9：00
        Call call = setupCall(8, 9);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(0, offPeakDuration);
        assertEquals(3600, peakDuration);

    }

    @Test
    public void testInOneDayAllInOffPeak() {
        // call was make from 5:00 to 6：00
        Call call = setupCall(5, 6);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(3600, offPeakDuration);
        assertEquals(0, peakDuration);
    }

    @Test
    public void testInOneDayCrossStartOfPeak() {
        // call was make from 6:00 to 8:00
        Call call = setupCall(6, 8);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(3600, offPeakDuration);
        assertEquals(3600, peakDuration);
    }

    @Test
    public void testInOneDayCrossEndOfPeak() {
        // call was make from 18:00 - 20:00
        Call call = setupCall(18, 20);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(3600, offPeakDuration);
        assertEquals(3600, peakDuration);
    }

    @Test
    public void testInOneDayCrossWholePeakTime() {
        // call was make from 6:00 - 20:00
        Call call = setupCall(6, 20);
        call = new Call(fakeCallStart, fakeCallEnd);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(7200, offPeakDuration);
        assertEquals(43200, peakDuration);
    }

    @Test
    public void testCrossDayOP2O() {
        // call was made from 2：00 - 1:00 the next day
        Call call = setupCall(2, 25);
        call = new Call(fakeCallStart, fakeCallEnd);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(oneHour * 11 / 1000, offPeakDuration);
        assertEquals(oneHour * 12 / 1000, peakDuration);
    }

    @Test
    public void testCrossDayPO() {
        // call was made from 18：00 - 1:00 the next day
        Call call = setupCall(18, 25);
        call = new Call(fakeCallStart, fakeCallEnd);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(oneHour * 6 / 1000, offPeakDuration);
        assertEquals(oneHour * 1 / 1000, peakDuration);
    }

    @Test
    public void testCrossDayPOP() {
        // call was made from 18：00 - 8:00 the next day
        Call call = setupCall(18, 32);
        call = new Call(fakeCallStart, fakeCallEnd);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        //assertEquals(oneHour*12/1000,offPeakDuration);
        assertEquals(oneHour * 2 / 1000, peakDuration);
    }

    @Test
    public void testCrossDayO() {
        // call was made from 22：00 - 2:00 the next day
        Call call = setupCall(22, 26);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(oneHour * 4 / 1000, offPeakDuration);
        assertEquals(0, peakDuration);
    }

    @Test
    public void testCrossDayOP() {
        // call was made from 22：00 - 12:00 the next day
        Call call = setupCall(22, 36);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(oneHour * 9 / 1000, offPeakDuration);
        assertEquals(oneHour * 5 / 1000, peakDuration);
    }

    @Test
    public void testCrossDayO2PO() {
        // call was made from 22：00 - 20:00 the next day
        Call call = setupCall(22, 44);
        int[] duration = engine.calculateDuration(call);
        int offPeakDuration = duration[0];
        int peakDuration = duration[1];
        assertEquals(oneHour * 10 / 1000, offPeakDuration);
        assertEquals(oneHour * 12 / 1000, peakDuration);
    }

}
