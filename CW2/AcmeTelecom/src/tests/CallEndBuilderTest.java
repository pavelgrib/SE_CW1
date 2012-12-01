package tests;

import com.acmetelecom.Builders.CallEndBuilder;
import com.acmetelecom.CallEnd;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 01:27
 * To change this template use File | Settings | File Templates.
 */
public class CallEndBuilderTest {

    @Test
    public void callerTest(){
        CallEnd callEnd = CallEndBuilder.aCallEnd().withCaller("Alice").withCallee("Bob").withEndTime(new Long(1990)).build();
        assertEquals(callEnd.getCaller(),"Alice");
    }

    @Test
    public void calleeTest(){
        CallEnd callEnd = CallEndBuilder.aCallEnd().withCaller("Alice").withCallee("Bob").withEndTime(new Long(1990)).build();
        assertEquals(callEnd.getCallee(),"Bob");
    }

    @Test
    public void endTimeTest(){

        long time = new Long(1990);
        CallEnd callEnd = CallEndBuilder.aCallEnd().withCaller("Alice").withCallee("Bob").withEndTime(time).build();
        assertEquals(callEnd.time(),time);
    }
}
