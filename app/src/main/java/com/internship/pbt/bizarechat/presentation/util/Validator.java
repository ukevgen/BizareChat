package com.internship.pbt.bizarechat.presentation.util;


public class Validator {

    private final String PASSWORD_REGEX = "(" + // TODO Password REGEX
            "(?=(.*d){2,})|" +
            "(.*\\[a-z])|" +
            "(?=(.*[A-Z]){2,})" +
            ")";
    private final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final String PHONE_REGEX = "(\\+[0-9]+[\\- \\.]*)?"
            + "(\\([0-9]+\\)[\\- \\.]*)?"
            + "([0-9][0-9\\- \\.]+[0-9])";

    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_REGEX);
    }

    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public boolean isPasswordLengthMatches(String password) {
        return password.length() >= 6 && password.length() <= 12;
    }
}
