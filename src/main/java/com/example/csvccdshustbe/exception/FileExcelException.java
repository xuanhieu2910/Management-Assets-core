package com.example.csvccdshustbe.exception;

public class FileExcelException extends Exception {

    private String message;
    public FileExcelException(String message) {
        super(message);
    }
}
