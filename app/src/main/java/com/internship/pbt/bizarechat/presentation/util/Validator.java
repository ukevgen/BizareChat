package com.internship.pbt.bizarechat.presentation.util;


import android.util.Patterns;

public class Validator {

    public boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        return Patterns.PHONE.matcher(phoneNumber.trim()).matches();
    }
}
