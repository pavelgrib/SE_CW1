package com.acmetelecom;

import java.io.PrintStream;

class HtmlPrinter implements Printer {

    private static Printer instance = new HtmlPrinter(System.out);

    private PrintStream printStream;

    public HtmlPrinter(PrintStream ps) {
        this.printStream = ps;
    }

    public static Printer getInstance() {
        return instance;
    }

    public void printHeading(String name, String phoneNumber, String pricePlan) {
        beginHtml();
        printStream.println(h2(name + "/" + phoneNumber + " - " + "Price Plan: " + pricePlan));
        beginTable();
    }

    private void beginTable() {
        printStream.println("<table border=\"1\">");
        printStream.println(tr(th("Time") + th("Number") + th("Duration") + th("Cost")));
    }

    private void endTable() {
        printStream.println("</table>");
    }

    private String h2(String text) {
        return "<h2>" + text + "</h2>";
    }

    public void printItem(String time, String callee, String duration, String cost) {
        printStream.println(tr(td(time) + td(callee) + td(duration) + td(cost)));
    }

    private String tr(String text) {
        return "<tr>" + text + "</tr>";
    }

    private String th(String text) {
        return "<th width=\"160\">" + text + "</th>";
    }

    private String td(String text) {
        return "<td>" + text + "</td>";
    }

    public void printTotal(String total) {
        endTable();
        printStream.println(h2("Total: " + total));
        endHtml();
    }

    private void beginHtml() {
        printStream.println("<html>");
        printStream.println("<head></head>");
        printStream.println("<body>");
        printStream.println("<h1>");
        printStream.println("Acme Telecom");
        printStream.println("</h1>");
    }

    private void endHtml() {
        printStream.println("</body>");
        printStream.println("</html>");
    }
}
