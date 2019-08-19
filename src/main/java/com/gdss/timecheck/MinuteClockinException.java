package com.gdss.timecheck;

public class MinuteClockinException extends Exception {

    private static final String error = "Only one register in the same minute is allowed in the system.";

    public MinuteClockinException() {
        super(error);
    }

}
