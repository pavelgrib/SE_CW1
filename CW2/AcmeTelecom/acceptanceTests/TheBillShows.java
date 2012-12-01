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


    public static class Record {
        public int LineNum;
        public String Text;

        public Record( String text,int num) {
            this.LineNum = num;
            Text = text;
        }
    }

    @Override
    public Class<?> getTargetClass() {
        return Record.class;
    }

    @Override
    public Object[] query() throws Exception {
        SystemUnderTest.bs.createCustomerBills();

        int i = 0;
        String[] out = SystemUnderTest.os.toString().split("\n");
        Record[] records = new Record[out.length];
        for( String line : out)
        {
            records[i] =new Record(line,i+1);
            i++;
        }
        return records;
    }
}
