package com.acmetelecom;

/*
Tiny type to encapsulate callee's name
 */
public class Callee {
    private final String callee;

    public Callee(String callee) {
        this.callee = callee;
    }

    public String toString() {
        return callee;
    }

}
