package com.example.csvccdshustbe.exception;

public class FileException extends Exception {

    private String message;

    public FileException(String message){
        super(message);
    }
}
