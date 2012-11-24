package fake;

import com.acmetelecom.Printer;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-23
 * Time: 下午12:02
 */
public class FakePrinter implements Printer {

    private String time;
    private String callee;
    private String duration;
    private String cost;
    private String total;
    private String name;
    private String phoneNumber;


    @Override
    public void printHeading(String name, String phoneNumber, String pricePlan) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void printItem(String time, String callee, String duration, String cost) {
        this.time = time;
        this.callee = callee;
        this.duration = duration;
        this.cost = cost;
    }

    @Override
    public void printTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public String getCallee() {
        return callee;
    }

    public String getDuration() {
        return duration;
    }

    public String getCost() {
        return cost;
    }
}
