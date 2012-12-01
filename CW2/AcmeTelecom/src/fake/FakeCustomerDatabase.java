package fake;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-29
 * Time: 下午2:52
 */

/**
 * This class was used to simulate the small size of customer database
 * in order to facilitate the Fit Test
 */
public class FakeCustomerDatabase implements CustomerDatabase{
    private ArrayList<Customer> customers;

    public FakeCustomerDatabase()
    {
        customers = new ArrayList<Customer>();
    }

    public java.util.List<Customer> getCustomers()
    {
        return customers;
    }

    /**
     * Add a customer to the database
     * @param newCustomer New customer
     */
    public void addCustomer(Customer newCustomer)
    {
        customers.add(newCustomer);
    }

    /**
     * Clear the customer database
     */
    public void clear()
    {
        customers.clear();
    }
}
