package com.acmetelecom;

/*
Tiny type to encapsulate caller's name
 */
public class Caller {
    private final String caller;

    public Caller(String caller) {
        this.caller = caller;
    }

    public String toString() {
        return caller;
    }
}
