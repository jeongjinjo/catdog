package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class MemoException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
