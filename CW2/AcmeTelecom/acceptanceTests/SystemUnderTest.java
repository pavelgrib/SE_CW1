import com.acmetelecom.BillingSystemLogical;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.rate.RateEngine;
import fake.FakeCustomerDatabase;
import fake.FakeGenerator;
import fake.FakePrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-23
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class SystemUnderTest  {

    public static ByteArrayOutputStream os = new ByteArrayOutputStream();
    public static PrintStream ps = new PrintStream(os);
    public static FakePrinter fakePrinter = new FakePrinter(ps);
    public static FakeGenerator fakeGenerator = new FakeGenerator(fakePrinter);

    public static RateEngine rateEngine;
    public static FakeCustomerDatabase fakeCustomerDatabase = new FakeCustomerDatabase();
    public static TariffLibrary tariffLibrary = CentralTariffDatabase.getInstance();
    public static BillingSystemLogical bs;


    public SystemUnderTest(){

    }

    public static void setBs(){
        bs = new BillingSystemLogical(fakeCustomerDatabase,tariffLibrary,fakeGenerator,rateEngine);
    }

}
