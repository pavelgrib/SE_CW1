import com.acmetelecom.rate.PeakSeperateOffPeakRateEngine;
import com.acmetelecom.rate.ProfitableRateEngine;
import fit.ColumnFixture;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-29
 * Time: 下午4:26
 */
public class GivenPeakPeriod extends ColumnFixture {

    public String Start;
    public String End;

    @Override
    public void reset() {
        Start = null;
        End = null;
    }

    @Override
    public void execute() {

        int start = Integer.parseInt(Start.split(":")[0]);
        int end = Integer.parseInt(End.split(":")[0]);

        SystemUnderTest.rateEngine = new PeakSeperateOffPeakRateEngine(start,end);
    }
}
