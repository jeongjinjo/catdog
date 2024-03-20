package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class PetExcption {
    private ErrorCode errorCode;

    public PetExcption(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
