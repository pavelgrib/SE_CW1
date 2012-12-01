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

    private PrintStream printStream = null;

    public FakePrinter(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void printHeading(String name, String phoneNumber, String pricePlan) {
        printStream.print(name + " - " + phoneNumber + " - " + pricePlan+"\n");
    }

    @Override
    public void printItem(String time, String callee, String duration, String cost) {
        printStream.print(time+" - Call:"+callee+" - Duration:"+duration+" - Cost:"+cost+"\n");
    }

    @Override
    public void printTotal(String total) {
        printStream.print("Total: " + total + "\n");
    }

}
