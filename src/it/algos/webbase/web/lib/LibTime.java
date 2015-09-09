package it.algos.webbase.web.lib;

import java.sql.Timestamp;

/**
 * Created by gac on 06 set 2015.
 * .
 */
public abstract class LibTime {


    public static long currentLong() {
        return getBaseLong();
    }// end of static method

    public static long adessoLong() {
        return getBaseLong();
    }// end of static method

    public static long getLong() {
        return getBaseLong();
    }// end of static method


    public static Timestamp current() {
        return getBase();
    }// end of static method

    public static Timestamp adesso() {
        return getBase();
    }// end of static method

    public static Timestamp get() {
        return getBase();
    }// end of static method


    private static Timestamp getBase() {
        return new Timestamp(getBaseLong());
    }// end of static method

    private static long getBaseLong() {
        return System.currentTimeMillis();
    }// end of static method

}// end of abstract static class
