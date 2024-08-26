package com.example.csvccdshustbe.exception;

public class ValidateFiledException extends Exception {
    private String message;
    public ValidateFiledException(String message) {
        super(message);
    }
}
