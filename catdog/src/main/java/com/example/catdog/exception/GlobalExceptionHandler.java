package com.example.catdog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberExcption.class)
    private ResponseEntity<ErrorResponse> handleEmpException(MemberExcption memberExcption) {
        ErrorCode errorCode = memberExcption.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                    .errorCode(errorCode.getErrorCode())
                                                    .errorMessage(errorCode.getMessage())
                                                    .errorDateTime(LocalDateTime.now())
                                                    .build();
        return ResponseEntity.status(memberExcption.getErrorCode()
                                                    .getHttpStatus())
                            .body(errorResponse);
    }

    @ExceptionHandler(PetException.class)
    private ResponseEntity<ErrorResponse> handleEmpException(PetException petException) {
        ErrorCode errorCode = petException.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                    .errorCode(errorCode.getErrorCode())
                                                    .errorMessage(errorCode.getMessage())
                                                    .errorDateTime(LocalDateTime.now())
                                                    .build();
        return ResponseEntity.status(petException.getErrorCode().getHttpStatus())
                            .body(errorResponse);
    }

    @ExceptionHandler(CareGroupException.class)
    private ResponseEntity<ErrorResponse> handleEmpException(CareGroupException petException) {
        ErrorCode errorCode = petException.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.getErrorCode())
                .errorMessage(errorCode.getMessage())
                .errorDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(petException.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(TodoException.class)
    private ResponseEntity<ErrorResponse> handleEmpException(TodoException todoException) {
        ErrorCode errorCode = todoException.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.getErrorCode())
                .errorMessage(errorCode.getMessage())
                .errorDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(todoException.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MemoException.class)
    private ResponseEntity<ErrorResponse> handleEmpException(MemoException memoException) {
        ErrorCode errorCode = memoException.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.getErrorCode())
                .errorMessage(errorCode.getMessage())
                .errorDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(memoException.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
                                                                , HttpHeaders headers
                                                                , HttpStatusCode status
                                                                , WebRequest request) {

        Map<String, Object> body = new HashMap<>();

        body.put("statusCode", HttpStatus.BAD_REQUEST);
        body.put("timestamp", LocalDateTime.now());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("messages", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
