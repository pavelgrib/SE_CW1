package com.acmetelecom.rate;

import com.acmetelecom.Call;
import com.acmetelecom.Cost;
import com.acmetelecom.customer.Tariff;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-11-28
 * Time: 下午12:02
 */
public interface RateEngine {

    public Cost calculateCost(Call call, Tariff tariff);

}
