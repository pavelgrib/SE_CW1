package tests;

import Builders.CustomerBuilder;
import com.acmetelecom.customer.Customer;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 01/12/2012
 * Time: 01:43
 * To change this template use File | Settings | File Templates.
 */
public class CustomerBuilderTest {
    private String name = "Alice";
    private String phoneNumber="123456789";
    private String pricePlan="asdf";
    Customer cus = CustomerBuilder.aCustomer()
            .withFullName(name).withPhoneNumber(phoneNumber).ofPricePlane(pricePlan)
            .build();
    @Test
    public void nameTest(){
        assertEquals(cus.getFullName(),name);
    }

    @Test
    public void phoneNumberTest(){
        assertEquals(cus.getPhoneNumber(),phoneNumber);
    }

    @Test
    public void pricePlanTest(){
        assertEquals(cus.getPricePlan(),pricePlan);
    }
}
