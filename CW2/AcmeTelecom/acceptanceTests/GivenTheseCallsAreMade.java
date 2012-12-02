import com.acmetelecom.Callee;
import com.acmetelecom.Caller;
import fit.Parse;
import fit.ColumnFixture;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-23
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class GivenTheseCallsAreMade extends ColumnFixture {

    public String StartTime;
    public String EndTime;
    public String From;
    public String To;
    public int Duration;

    @Override
    public void reset() {
        StartTime = null;
        EndTime = null;
        From = null;
        To = null;
        Duration = 0;
    }

    @Override
    public void doRows(Parse rows) {
        SystemUnderTest.setBs();
        super.doRows(rows);
    }

    @Override
    public void execute() throws Exception {
        // For making the test easy, we read date from Calender object and set the time manually
        Calendar cal = Calendar.getInstance();

        int startHour = Integer.parseInt(StartTime.split(":")[0]);
        int startMinute = Integer.parseInt(StartTime.split(":")[1]);
        cal.set(Calendar.HOUR_OF_DAY , startHour);
        cal.set(Calendar.MINUTE , startMinute);
        cal.set(Calendar.SECOND , 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startInMills = cal.getTime().getTime();
        SystemUnderTest.bs.setTime(startInMills);
        SystemUnderTest.bs.callInitiated(new Caller(From), new Callee(To));
//
//        int endHour = Integer.parseInt(EndTime.split(":")[0]);
//        int endMinute = Integer.parseInt(EndTime.split(":")[1]);
//        cal.set(Calendar.HOUR_OF_DAY , endHour);
//        cal.set(Calendar.MINUTE , endMinute);
//        cal.set(Calendar.SECOND , 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        long endInMills = cal.getTime().getTime();
        long endInMills = startInMills + Duration*1000;
        SystemUnderTest.bs.setTime(endInMills);
        SystemUnderTest.bs.callCompleted(new Caller(From), new Callee(To));

    }

}
