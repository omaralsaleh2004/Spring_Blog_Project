package com.Omar.Spring_Blog_Project.exception;

public record ErrorResponse(
        int status,
        String error,
        String message
) {
}
