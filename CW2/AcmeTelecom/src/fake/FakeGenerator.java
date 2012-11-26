package fake;

import com.acmetelecom.*;
import com.acmetelecom.customer.Customer;

import java.io.PrintStream;
import java.util.List;

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
    public void send(Customer customer, List<LineItem> calls, String totalBill) {
//        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
//        for (LineItem call : calls) {
//            printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
//        }
        printer.printTotal(totalBill);
    }
}
