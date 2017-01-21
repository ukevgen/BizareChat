package com.internship.pbt.bizarechat.presentation.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final String PASSWORD_REGEX = "((?=.*\\d).{2,})((?=.*[a-z]))((?=.*[A-Z]).{2,})";
    private final String EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";
    private final String PHONE_REGEX =  "(\\+[0-9]+[\\- \\.]*)?"
            + "(\\([0-9]+\\)[\\- \\.]*)?"
            + "([0-9][0-9\\- \\.]+[0-9])";

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
        return password.length() >= 6 && password.length() <= 12;
    }
}
