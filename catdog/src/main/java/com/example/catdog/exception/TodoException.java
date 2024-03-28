package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException {
    private final ErrorCode errorCode;

    public TodoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
