package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class CareGroupException extends RuntimeException {
    private ErrorCode errorCode;

    public CareGroupException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
