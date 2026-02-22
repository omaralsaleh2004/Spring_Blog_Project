package com.Omar.Spring_Blog_Project.exception;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
