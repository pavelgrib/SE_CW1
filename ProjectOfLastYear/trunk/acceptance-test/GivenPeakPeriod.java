import com.acmetelecom.rates.OffpeakFairRateSelector;
import fit.ColumnFixture;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30/11/2011
 * Time: 4:38 μμ
 * To change this template use File | Settings | File Templates.
 */
public class GivenPeakPeriod extends ColumnFixture
{
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

        SystemUnderTest.selector = new OffpeakFairRateSelector(start,end);
	}
}
