package com.example.exception;

public class DuplicatesException extends RuntimeException {
    public DuplicatesException(String message) {
        super(message);
    }
}