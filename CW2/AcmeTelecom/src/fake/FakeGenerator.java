package fake;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.Generator;
import com.acmetelecom.LineItem;
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

    @Override
    public void send(Customer customer, List<LineItem> calls, String totalBill) {

    }
}
