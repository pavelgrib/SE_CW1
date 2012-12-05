package com.acmetelecom;

import com.acmetelecom.customer.Customer;

/*
The class for printing bills
Refactored so that we can pass a specific Printer in constructor
 */
public class BillGenerator implements Generator {
    private Printer printer;

    public BillGenerator(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void send(Customer customer, Iterable<LineItem> calls, String totalBill) {

        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (LineItem call : calls) {
            printer.printItem(call.date(), call.callee().toString(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

}
