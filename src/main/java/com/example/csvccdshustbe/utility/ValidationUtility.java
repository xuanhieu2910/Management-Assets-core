package com.example.csvccdshustbe.utility;

import teamit.hust.ktxcdshustbe.exception.ValidateFiledException;

public class ValidationUtility {

    public final static String MESSAGE_STRONG_PASSWORD = "Must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";
    public final static String PATTERN_STRONG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$";


    public static boolean validateStrongPassword(String password) throws ValidateFiledException {
        if (password != null) {
            return password.matches(PATTERN_STRONG_PASSWORD);
        } else {
            throw new ValidateFiledException(MESSAGE_STRONG_PASSWORD);
        }
    }
}
