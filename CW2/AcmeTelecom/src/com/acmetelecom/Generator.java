package com.acmetelecom;

import com.acmetelecom.customer.Customer;

import java.io.PrintStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 11/24/12
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Generator {
    void send(Customer customer, List<LineItem> calls, String totalBill);
}
