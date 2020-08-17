package com.example.beckart.utils;

import android.util.Patterns;

public class Validation {

    private static int PASSWORD_MIN_LENGTH = 8;
    private static int NAME_MIN_LENGTH = 3;
    private static int ZIP_LENGTH = 6;

    public static boolean isValidEmail(String email){
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone){
        return !Patterns.PHONE.matcher(phone).matches();
    }
    public static boolean isValidPassword(String password){
        return password.length() >= PASSWORD_MIN_LENGTH;
    }

    public static boolean isValidName(String name){
        return name.length() >= NAME_MIN_LENGTH;
    }

    public static boolean isValidZip(String zip) {
        return zip.length()!=ZIP_LENGTH;
    }
}
