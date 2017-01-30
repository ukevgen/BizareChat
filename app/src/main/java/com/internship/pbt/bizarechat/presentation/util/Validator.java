package com.internship.pbt.bizarechat.presentation.util;


import java.io.File;

public class Validator {

    private static final String TAG = "Validator";

    private static final int SIX = 6;
    private static final int TWELVE = 12;
    public static final String LETTERS = "[\\D+]";
    private final String PASSWORD_REGEX = "(?=(.*\\d){2})(?=(.*[a-z]))(?=(.*[A-Z]){2}).*";
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
        return password.length() >= SIX && password.length() <= TWELVE;
    }

    public boolean isPasswordMatch(String password, String confirmPsw) {
        return password.equals(confirmPsw);
    }

    public boolean isValidAvatarSize(File file){
        return calculateIsValidAvatarByteSize(file);
    }

    private boolean calculateIsValidAvatarByteSize(File file){
        return file.length() < 1024*1024 && file.length() != 0;
    }


    public String toApiPhoneFormat(String number) {
        return number.replaceAll(LETTERS, "");
    }
}