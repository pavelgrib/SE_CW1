package fake;

import com.acmetelecom.CallEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-24
 * Time: 下午3:27
 */
public class FakeCallEnd extends CallEvent {

    public FakeCallEnd(String caller, String callee, long time) {
        super(caller, callee, time);
    }
}
