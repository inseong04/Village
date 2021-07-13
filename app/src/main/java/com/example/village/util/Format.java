package com.example.village.util;

public class Format {

    public static String phoneNumberFormat (String number) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
        return number.replaceAll(regEx, "$1-$2-$3");
    }
}
