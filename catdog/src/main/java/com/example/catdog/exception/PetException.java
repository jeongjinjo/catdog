package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class PetException extends RuntimeException {
    private ErrorCode errorCode;

    public PetException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
