package com.internship.pbt.bizarechat.presentation.util;


import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final int SIX = 6;
    private static final int TWELVE = 12;
    private final String PASSWORD_REGEX = "((?=.*\\d).{2,})((?=.*[a-z]))((?=.*[A-Z]).{2,})";
    private final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final String PHONE_REGEX =  "(\\+[0-9]+[\\- \\.]*)?"
            + "(\\([0-9]+\\)[\\- \\.]*)?"
            + "([0-9][0-9\\- \\.]+[0-9])";

    public boolean isValidEmail(String email) {
        Log.d("123", "Validator " + email);

        Log.d("123", "Validator " + email.matches(EMAIL_REGEX));

        return email.matches(EMAIL_REGEX);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        Pattern patterns = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = patterns.matcher(password);
        return matcher.matches();
    }

    public boolean isPasswordLengthMatches(String password) {
        return password.length() >= SIX && password.length() <= TWELVE;
    }

    public boolean isPasswordMatch (String password, String confirmPsw){
        return password.equals(confirmPsw);
    }
}
