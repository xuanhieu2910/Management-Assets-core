package com.example.csvccdshustbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleException extends Exception{

    private String message;

    public RoleException(String message){
        super(message);
    }
}
