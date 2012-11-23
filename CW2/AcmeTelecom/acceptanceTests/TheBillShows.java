import fit.RowFixture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-23
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class TheBillShows extends RowFixture{

    public static class Row {
        public int Line;
        public String Text;

        public Row(int line, String text) {
            Line = line;
            Text = text;
        }
    }

    @Override
    public Class<?> getTargetClass() {
        return Row.class;
    }

    @Override
    public Object[] query() throws Exception {
        SystemUnderTest.bs.createCustomerBills();

        List<Row> rows = new ArrayList<Row>();
        rows.add(new Row(rows.size() + 1, "Total"));
        //TODO get contents from Printer and add to rows
        return rows.toArray();
    }
}
