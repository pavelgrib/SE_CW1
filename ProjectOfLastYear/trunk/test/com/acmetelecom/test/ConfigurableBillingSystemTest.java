package com.acmetelecom.test;

import static com.acmetelecom.builders.CustomerBuilder.aCustomer;
import static com.acmetelecom.builders.Second.Seconds;
import static com.acmetelecom.builders.Minute.Minutes;
import static com.acmetelecom.builders.RateDescriptorBuilder.aRateDescriptor;
import static org.hamcrest.Matchers.equalTo;

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.acmetelecom.fakes.FakeClock;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.ConfigurableBillingSystem;
import com.acmetelecom.Generator;
import com.acmetelecom.LineItem;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rates.RateDescriptor;
import com.acmetelecom.rates.RateSelector;

public class ConfigurableBillingSystemTest {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	// Matcher for an iterable collection as a specific size
	private static org.hamcrest.Matcher<Iterable<LineItem>> iterableWithSize(int size) {
		return org.hamcrest.collection.IsIterableWithSize.<LineItem>iterableWithSize(size);
	}
	
	// Matcher for any string
	private static org.hamcrest.Matcher<String> anyString() {
		return new org.hamcrest.core.IsAnything<String>();
	}

	// Matcher for any call
	private static org.hamcrest.Matcher<Call> anyCall() {
		return new org.hamcrest.core.IsAnything<Call>();
	}
	
	// Build collection of rates using a variable length parameters
	// to make it a little more clear when writing tests below
	private static Collection<RateDescriptor> someRates(RateDescriptor... rateDescs) {
		Collection<RateDescriptor> rates = new LinkedList<RateDescriptor>();
		
		for(RateDescriptor rd : rateDescs)
			rates.add(rd);
		
		return rates;
	}
	
	public ConfigurableBillingSystemTest() {}
	
	@Test
	public void testCreateCustomerBillWithNoCalls() {
    	final CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);
    	final TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    	final Generator generator = context.mock(Generator.class);
    	final RateSelector selector = context.mock(RateSelector.class);
    	
    	// Fake clock for the test
    	FakeClock clock = new FakeClock(Calendar.getInstance());
    	ConfigurableBillingSystem bs = new ConfigurableBillingSystem(
    										customerDatabase, 
    										tariffLibrary, 
    										generator,
    										selector,
    										clock);

    	// Customer for the test
    	final Customer c1 = aCustomer().
								named("John Smith").
								withPhoneNumber("447766406373").build();
    	
    	final List<Customer> customers = new LinkedList<Customer>();
    	customers.add(c1);
    	
    	context.checking(new Expectations() {{
    		allowing(customerDatabase).getCustomers(); will(returnValue(customers));
    		oneOf(generator).send(
    							with(equalTo(c1)), 
    							with(iterableWithSize(0)), 
    							with(anyString()));
    	}});
    	
    	bs.createCustomerBills();    	
	} 
	
	@Test
	public void testCreateCustomerBillsWithSingleCallAndTwoCustomers() throws InterruptedException {
    	final CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);
    	final TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    	final Generator generator = context.mock(Generator.class);
    	final RateSelector selector = context.mock(RateSelector.class);
    	
    	FakeClock clock = new FakeClock(Calendar.getInstance());
    	ConfigurableBillingSystem bs = new ConfigurableBillingSystem(
    										customerDatabase, 
    										tariffLibrary, 
    										generator,
    										selector,
    										clock);
    	
    	// Create the customers needed for this test
    	final Customer c1 = aCustomer().
    							named("John Smith").
    							withPhoneNumber("447766406373").build();
    							
    	final Customer c2 = aCustomer().
    							named("Sarah Jones").
    							withPhoneNumber("4479204334499").build();
    	
    	final List<Customer> customers = new LinkedList<Customer>();
    	customers.add(c1);
    	customers.add(c2);
    	
    	// We can test the protocol between the billing system and some of 
    	// its collaborators, but not all of them.
    	context.checking(new Expectations() {{
    		allowing(customerDatabase).getCustomers(); will(returnValue(customers));
    		allowing(selector).determineRates(with(Tariff.Business), with(anyCall()));
    		
    		oneOf(tariffLibrary).tarriffFor(c1); will(returnValue(Tariff.Business));
    				
    		oneOf(generator).send(
					with(equalTo(c1)), 
					with(iterableWithSize(1)), 
					with(anyString()));
    		
    		oneOf(generator).send(
					with(equalTo(c2)), 
					with(iterableWithSize(0)), 
					with(anyString()));    		
    	}});
    	
    	bs.callInitiated(c1.getPhoneNumber(), c2.getPhoneNumber());
    	clock.moveForward(Seconds(10));
    	bs.callCompleted(c1.getPhoneNumber(), c2.getPhoneNumber());
    	bs.createCustomerBills();
	}

	@Test
	public void testCreateCustomerBillsWithTwoCallsAndTwoCustomers() throws InterruptedException {
    	final CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);
    	final TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    	final Generator generator = context.mock(Generator.class);
    	final RateSelector selector = context.mock(RateSelector.class);
    	
    	FakeClock clock = new FakeClock(Calendar.getInstance());
    	ConfigurableBillingSystem bs = new ConfigurableBillingSystem(
    										customerDatabase, 
    										tariffLibrary, 
    										generator,
    										selector,
    										clock);

    	// Create the customers needed for this test    	
    	final Customer c1 = aCustomer().
    							named("John Smith").
    							withPhoneNumber("447766406373").build();
    							
    	final Customer c2 = aCustomer().
    							named("Sarah Jones").
    							withPhoneNumber("4479204334499").build();
    	
    	final List<Customer> customers = new LinkedList<Customer>();
    	customers.add(c1);
    	customers.add(c2);
    	
    	// We can test the protocol between the billing system and some of 
    	// its collaborators, but not all of them.
    	context.checking(new Expectations() {{
    		allowing(customerDatabase).getCustomers(); will(returnValue(customers));
    		allowing(selector).determineRates(with(Tariff.Business), with(anyCall()));
    		
    		oneOf(tariffLibrary).tarriffFor(c1); will(returnValue(Tariff.Business));
    		oneOf(generator).send(
					with(equalTo(c1)), 
					with(iterableWithSize(1)), 
					with(anyString()));
    		
    		oneOf(tariffLibrary).tarriffFor(c2); will(returnValue(Tariff.Business));
    		oneOf(generator).send(
					with(equalTo(c2)), 
					with(iterableWithSize(1)), 
					with(anyString()));    		
    	}});
    	
    	// Call the billing system moving the clock forward as much as necessary
    	bs.callInitiated(c1.getPhoneNumber(), c2.getPhoneNumber());
    	clock.moveForward(Seconds(10));
    	bs.callCompleted(c1.getPhoneNumber(), c2.getPhoneNumber());

    	bs.callInitiated(c2.getPhoneNumber(), c1.getPhoneNumber());
    	clock.moveForward(Seconds(20));
    	bs.callCompleted(c2.getPhoneNumber(), c1.getPhoneNumber());
    	
    	bs.createCustomerBills();
	}
	
	@Test
	public void testCreateCustomerBillsWithSingleCallAndTwoCustomersAmountOffpeak() throws InterruptedException {
    	final CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);
    	final TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    	final Generator generator = context.mock(Generator.class);
    	final RateSelector selector = context.mock(RateSelector.class);
    	
    	// Set base time for the test. Completely off-peak call
    	FakeClock clock = new FakeClock(Calendar.getInstance());
    	
    	ConfigurableBillingSystem bs = new ConfigurableBillingSystem(
    										customerDatabase, 
    										tariffLibrary, 
    										generator,
    										selector,
    										clock);
    	
    	final Customer c1 = aCustomer().
    							named("John Smith").
    							withPhoneNumber("447766406373").build();
    							
    	final Customer c2 = aCustomer().
    							named("Sarah Jones").
    							withPhoneNumber("4479204334499").build();
    	
    	final List<Customer> customers = new LinkedList<Customer>();
    	customers.add(c1);
    	customers.add(c2);
    	
    	// We can test the protocol between the billing system and some of 
    	// its collaborators, but not all of them.
    	context.checking(new Expectations() {{
    		allowing(customerDatabase).getCustomers(); 
    			will(returnValue(customers));
    		
    		allowing(selector).determineRates(with(Tariff.Business), with(anyCall()));
    			will(returnValue(someRates(
    					aRateDescriptor().
    						charging(Tariff.Business.offPeakRate()).
    						withDuration(Minutes(10)).build())));
    		
    		oneOf(tariffLibrary).tarriffFor(c1); will(returnValue(Tariff.Business));

    		// We want to assert that the generator gets the right amount for the bill    		
    		oneOf(generator).send(
					with(equalTo(c1)), 
					with(iterableWithSize(1)), 
					with("1.80"));
    		
    		oneOf(generator).send(
					with(equalTo(c2)), 
					with(iterableWithSize(0)), 
					with(anyString()));    		
    	}});
    	
    	bs.callInitiated(c1.getPhoneNumber(), c2.getPhoneNumber());
    	clock.moveForward(Minutes(10));
    	bs.callCompleted(c1.getPhoneNumber(), c2.getPhoneNumber());
    	
    	bs.createCustomerBills();
	}
	
	@Test
	public void testCreateCustomerBillsWithSingleCallAndTwoCustomersAmountOnpeak() throws InterruptedException {
    	final CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);
    	final TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    	final Generator generator = context.mock(Generator.class);
    	final RateSelector selector = context.mock(RateSelector.class);
    	
    	// Set base time for the test. Completely off-peak call
    	FakeClock clock = new FakeClock(Calendar.getInstance());
    	
    	ConfigurableBillingSystem bs = new ConfigurableBillingSystem(
    										customerDatabase, 
    										tariffLibrary, 
    										generator,
    										selector,
    										clock);
    	
    	final Customer c1 = aCustomer().
    							named("John Smith").
    							withPhoneNumber("447766406373").build();
    							
    	final Customer c2 = aCustomer().
    							named("Sarah Jones").
    							withPhoneNumber("4479204334499").build();
    	
    	final List<Customer> customers = new LinkedList<Customer>();
    	customers.add(c1);
    	customers.add(c2);
    	
    	// We can test the protocol between the billing system and some of 
    	// its collaborators, but not all of them.
    	context.checking(new Expectations() {{
    		allowing(customerDatabase).getCustomers(); 
    			will(returnValue(customers));
    		
    		allowing(selector).determineRates(with(Tariff.Standard), with(anyCall()));
    			will(returnValue(someRates(
    					aRateDescriptor().
    						charging(Tariff.Standard.peakRate()).
    						withDuration(Minutes(10)).build())));
    		
    		oneOf(tariffLibrary).tarriffFor(c1); will(returnValue(Tariff.Standard));

    		// We want to assert that the generator gets the right amount for the bill
    		oneOf(generator).send(
					with(equalTo(c1)), 
					with(iterableWithSize(1)), 
					with("3.00"));
    		
    		oneOf(generator).send(
					with(equalTo(c2)), 
					with(iterableWithSize(0)), 
					with(anyString()));    		
    	}});
    	
    	// Now call the billing system
    	bs.callInitiated(c1.getPhoneNumber(), c2.getPhoneNumber());
    	clock.moveForward(Minutes(10));
    	bs.callCompleted(c1.getPhoneNumber(), c2.getPhoneNumber());
    	
    	bs.createCustomerBills();
	}	
}

