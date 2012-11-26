package fake;

import com.acmetelecom.Printer;

import java.io.PrintStream;

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

    private PrintStream printStream = null;

    public FakePrinter(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void printHeading(String name, String phoneNumber, String pricePlan) {
        printStream.println(name + "/" + phoneNumber + " - " + "Price Plan: " + pricePlan);
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
        printStream.print("Total:" + total + "\n");
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
