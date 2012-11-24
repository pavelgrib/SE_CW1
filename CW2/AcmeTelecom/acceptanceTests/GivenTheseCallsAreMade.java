
import fit.Parse;
import fit.ColumnFixture;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-23
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class GivenTheseCallsAreMade extends ColumnFixture{

    public String From;
    public String To;
    public int Duration;

    @Override
    public void reset() {
        From = null;
        To = null;
        Duration = 0;
    }

    @Override
    public void doRows(Parse rows) {
        super.doRows(rows);
    }

    @Override
    public void execute() throws Exception
    {
        SystemUnderTest.bs.callInitiated(From,To);
        Thread.sleep(Duration * 1000);
        SystemUnderTest.bs.callCompleted(From,To);

    }

}
