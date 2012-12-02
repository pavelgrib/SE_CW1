package tests;

import com.acmetelecom.Cost;
import com.acmetelecom.MoneyFormatter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 29/11/2012
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class MoneyFormatterTest {
    static MoneyFormatter moneyFormatter = new MoneyFormatter();

    @Test
    public void testPenceToPounds(){
        Cost moneyInPence = new Cost(new BigDecimal(100000));
        assertTrue(moneyFormatter.penceToPounds(moneyInPence).equals("1000.00"));
    }
}
