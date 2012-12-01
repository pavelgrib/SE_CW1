package tests;

import com.acmetelecom.Builders.CallStartBuilder;
import com.acmetelecom.CallStart;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 01:32
 * To change this template use File | Settings | File Templates.
 */
public class CallStartBuilderTest {
    @Test
    public void callerTest(){
        CallStart callstart = CallStartBuilder.aCallStart().withCaller("Alice").withCallee("Bob").withStartTime(new Long(1990)).build();
        assertEquals(callstart.getCaller(),"Alice");
    }

    @Test
    public void calleeTest(){
        CallStart callstart = CallStartBuilder.aCallStart().withCaller("Alice").withCallee("Bob").withStartTime(new Long(1990)).build();
        assertEquals(callstart.getCallee(),"Bob");
    }

    @Test
    public void endTimeTest(){

        long time = new Long(1990);
        CallStart callstart = CallStartBuilder.aCallStart().withCaller("Alice").withCallee("Bob").withStartTime(new Long(1990)).build();
        assertEquals(callstart.time(),time);
    }
}
