package com.internship.pbt.bizarechat.presentation.util;


import android.util.Patterns;

public class Validator {

    private final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}).{1,}";
    public boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        return Patterns.PHONE.matcher(phoneNumber.trim()).matches();
    }

    public boolean isValidPassword(String password){
        return false; //TODO password validator
    }
}
