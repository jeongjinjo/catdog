package com.example.catdog.exception;

import lombok.Getter;

@Getter
public class MemberExcption extends RuntimeException{
    private ErrorCode errorCode;

    public MemberExcption(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
