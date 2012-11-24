package fake;

import com.acmetelecom.CallEvent;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-24
 * Time: 下午3:22
 */
public class FakeCallStart extends CallEvent {
    public FakeCallStart(String caller, String callee, long time) {
        super(caller, callee, time);
    }
}


// (new Date(2012, 11, 24, 18, 55, 0)).getTime()