package fake;

import com.acmetelecom.*;
import com.acmetelecom.customer.Customer;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FakeGenerator implements Generator {

    private Printer printer;

    public FakeGenerator(Printer printer){
         this.printer = printer;
    }

    @Override
    public void send(Customer customer, Iterable<LineItem> calls, String totalBill) {

        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (LineItem call : calls) {
            printer.printItem(getTime(call.date()), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

    private String getTime(String date){
        String[] subStr = date.split(" ");
        return subStr[subStr.length-1];
    }
}
