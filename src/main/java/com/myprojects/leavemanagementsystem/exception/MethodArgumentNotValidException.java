package com.myprojects.leavemanagementsystem.exception;

public class MethodArgumentNotValidException extends RuntimeException{
    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
