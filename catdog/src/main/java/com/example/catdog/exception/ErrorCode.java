package com.example.catdog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "해당하는 내용이 없습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "유효성 검사를 실패하였습니다."),
    DUPLICATE(HttpStatus.CONFLICT, "DUPLICATE", "중복된 값이 존재합니다."),
    ID_OR_PASSWORD_FAILED(HttpStatus.BAD_REQUEST, "ID_AND_PASSWORD_FAILED", "아이디 또는 비밀번호가 틀렸습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
    GROUP_REGISTRATION_RESTRICTIONS(HttpStatus.BAD_REQUEST, "GROUP_REGISTRATION_RESTRICTIONS", "최대 3개까지만 보유할 수 있습니다."),
    LIMITED_NUMBER_OF_MEMBER_REGISTERED(HttpStatus.BAD_REQUEST,"LIMITED_NUMBER_OF_MEMBER_REGISTERED","최대 4명까지만 등록할 수 있습니다."),
    PET_REGISTRATION_RESTRICTIONS(HttpStatus.BAD_REQUEST, "PET_REGISTRATION_RESTRICTIONS", "최대 5마리까지만 등록할 수 있습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_NOT_FOUND", "그룹에는 최소 1명은 등록되어야 합니다.")
    ;

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
