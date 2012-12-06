package com.acmetelecom;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pg
 * Date: 12/6/12
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Transaction {
    String callee();

    int durationSeconds();

    String date();

    Date startTime();

    Date endTime();
}
