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
        // TODO modify to meet the need of html requirements
        printStream.print(name + "/" + phoneNumber + " - " + "Price Plan: " + pricePlan);
    }

    @Override
    public void printItem(String time, String callee, String duration, String cost) {
        // TODO modify to meet the need of html requirements
        printStream.print(time+" "+callee+" "+duration+" "+cost+"\n");
    }

    @Override
    public void printTotal(String total) {
        // TODO modify to meet the need of html requirements
        printStream.print("Total: " + total + "\n");
    }

}
