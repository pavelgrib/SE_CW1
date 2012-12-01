package tests;

import Builders.CustomerBuilder;
import com.acmetelecom.customer.Customer;
import fake.FakeCustomerDatabase;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-29
 * Time: 17:36
 */

/**
 * This is the Unit Test for the FakeCustomerDatabase class
 */
public class FakeCustomerDatabaseUnitTest {


    Customer cus1 = CustomerBuilder.aCustomer()
                    .withFullName("cus1")
                    .withPhoneNumber("07771111111")
                    .ofPricePlane("Business")
                    .build();

    Customer cus2 = CustomerBuilder.aCustomer()
                    .withFullName("cus2")
                    .withPhoneNumber("07772222222")
                    .ofPricePlane("Business")
                    .build();

    @Test
    public void testAddCustomers(){
        FakeCustomerDatabase db = new FakeCustomerDatabase();
        db.addCustomer(cus1);
        db.addCustomer(cus2);
        assertNotNull(db);
    }

    @Test
    public void testGetCustomers(){
        FakeCustomerDatabase db = new FakeCustomerDatabase();
        db.addCustomer(cus1);
        db.addCustomer(cus2);
        List<Customer>  customers = db.getCustomers();
        assertEquals(2, customers.size());
    }

    @Test
    public void testClearDatabase(){
        List<Customer> customers;
        FakeCustomerDatabase db = new FakeCustomerDatabase();
        db.addCustomer(cus1);
        db.addCustomer(cus2);
        customers = db.getCustomers();
        assertEquals(2,customers.size());
        db.clear();
        customers = db.getCustomers();
        assertEquals(0,customers.size());

    }
}
