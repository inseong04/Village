package com.example.village.util;

public class Expression {
    public static boolean isVaildPhoneNumber(String phoneNumber) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");
        return pattern.matcher(phoneNumber).matches();
    }

    public static boolean isVaildPw(String pw) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(^.*(?=.{6,24})(?=.*[0-9])(?=.*[A-z]).*$)");
        return pattern.matcher(pw).matches();
    }
}
