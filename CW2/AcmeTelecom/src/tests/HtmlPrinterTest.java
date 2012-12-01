package tests;

import Builders.CallBuilder;
import Builders.CallEndBuilder;
import Builders.CallStartBuilder;
import Builders.CustomerBuilder;
import com.acmetelecom.Call;
import com.acmetelecom.HtmlPrinter;
import com.acmetelecom.Printer;
import com.acmetelecom.customer.Customer;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class HtmlPrinterTest{

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    Printer printer = new HtmlPrinter(new PrintStream(this.out));

    /*
    html print format setup
    */
    private static final String HTML_BEGIN = "<html>";
    private static final String HTML_END = "</html>";
    private static final String HEAD_BEGIN = "<head>";
    private static final String HEAD_END = "</head>";
    private static final String BODY_BEGIN = "<body>";
    private static final String BODY_END = "</body>";


    private boolean htmlFormatChecker(String s) {
        return 	s.startsWith(HTML_BEGIN) &&
                s.contains(HEAD_BEGIN + HEAD_END) &&
                s.contains(BODY_BEGIN) &&
                s.contains(BODY_END) &&
                s.endsWith(HTML_END) &&
                s.contains("Acme Telecom");
    }

    private boolean containCustomerDetails(Customer c, String s){
        return s.contains("<h2>"+c.getPhoneNumber()+"/"+c.getFullName()+" - Price Plan: "+c.getPricePlan()+"</h2>");
    }

    private boolean containsCorrectCallHistory(Call call, Customer c2, String s, String cost){
        return s.contains("<td>"+call.date().toString()+"</td><td>"+c2.getFullName()+"</td><td>"+call.durationSeconds()+"</td><td>"+cost+"</td>");
    }

    private String removeSpacesAndNewLine(String s){
        return s.replaceAll("\n", "").replaceAll("\r", "").trim();
    }

    private String billGenerator(){

        Customer cus1 = CustomerBuilder.aCustomer()
                .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
                .build();
        Customer cus2 = CustomerBuilder.aCustomer()
                .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
                .build();
        Call call = CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1000))
                        .build()
        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2000))
                        .build()
        ).build();

        String cost = "99.99";

        printer.printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());
        printer.printItem(call.startTime().toString(), cus2.getPhoneNumber(), Integer.toString(call.durationSeconds()), cost);
        printer.printTotal(cost);

        return removeSpacesAndNewLine(this.out.toString());
    }
    /***********/

    @Test
    public void testValidHtml(){
        assertTrue(htmlFormatChecker(billGenerator()));
    }

    @Test
    public void testCallerDetails(){
        Customer cus1 = CustomerBuilder.aCustomer()
                .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
                .build();
        Customer cus2 = CustomerBuilder.aCustomer()
                .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
                .build();
        Call call = CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1000))
                        .build()
        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2000))
                        .build()
        ).build();

        String cost = "99.99";

        printer.printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());
        printer.printItem(call.startTime().toString(), cus2.getPhoneNumber(), Integer.toString(call.durationSeconds()), cost);
        printer.printTotal(cost);

        containCustomerDetails(cus1,removeSpacesAndNewLine(this.out.toString()));
    }

    @Test
    public void testCallHistory(){
        Customer cus1 = CustomerBuilder.aCustomer()
                .withFullName("Alice").withPhoneNumber("123456789").ofPricePlane("asdf")
                .build();
        Customer cus2 = CustomerBuilder.aCustomer()
                .withFullName("Bob").withPhoneNumber("987654321").ofPricePlane("asdf")
                .build();
        Call call = CallBuilder.aCall().startAt(
                CallStartBuilder.aCallStart()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withStartTime(new Long(1000))
                        .build()
        ).endAt(
                CallEndBuilder.aCallEnd()
                        .withCaller(cus1.getPhoneNumber()).withCallee(cus2.getPhoneNumber()).withEndTime(new Long(2000))
                        .build()
        ).build();

        String cost = "99.99";

        printer.printHeading(cus1.getFullName(), cus1.getPhoneNumber(), cus1.getPricePlan());
        printer.printItem(call.startTime().toString(), cus2.getPhoneNumber(), Integer.toString(call.durationSeconds()), cost);
        printer.printTotal(cost);

        containsCorrectCallHistory(call,cus2,removeSpacesAndNewLine(this.out.toString()),cost);
    }

}