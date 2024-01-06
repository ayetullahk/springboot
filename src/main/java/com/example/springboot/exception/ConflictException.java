package com.example.springboot.exception;

public class ConflictException extends RuntimeException{
    public ConflictException (String message){
        super(message);
    }
}
