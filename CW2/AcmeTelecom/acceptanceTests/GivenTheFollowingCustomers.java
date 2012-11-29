import com.acmetelecom.customer.Customer;
import fit.ColumnFixture;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-29
 * Time: 下午3:27
 */
public class GivenTheFollowingCustomers extends ColumnFixture {

    public String Name;
    public String PhoneNumber;
    public String TariffPlan;

    @Override
    public void reset() {
        Name = null;
        PhoneNumber = null;
        TariffPlan = null;
    }

    @Override
    public void execute() {
        Customer customer = new Customer(Name,PhoneNumber,TariffPlan);
        SystemUnderTest.fakeCustomerDatabase.addCustomer(customer);
    }
}
