package Builders;

import com.acmetelecom.customer.Customer;
import com.sun.istack.internal.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class CustomerBuilder implements Builder {
    private String phoneNumber;
    private String fullName;
    private String pricePlan;

    public static CustomerBuilder aCustomer(){
        return new CustomerBuilder();
    }

    public CustomerBuilder(){
        this.phoneNumber=null;
        this.fullName=null;
        this.pricePlan=null;
    }

    public CustomerBuilder withFullName(String fullName){
        this.fullName=fullName;
        return this;
    }

    public CustomerBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
        return this;
    }

    public CustomerBuilder ofPricePlane(String pricePlan){
        this.pricePlan=pricePlan;
        return this;
    }


    @Override
    public Customer build() {
        return new Customer(fullName,phoneNumber,pricePlan);
    }
}
