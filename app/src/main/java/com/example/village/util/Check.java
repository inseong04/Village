package com.example.village.util;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    public static Boolean emailCheck(String email) {
        Pattern pattern = Pattern.compile("/^[a-z0-9_-]{6,18}$/");

        return pattern.matcher(email).matches() ? true : false;
    }
}
