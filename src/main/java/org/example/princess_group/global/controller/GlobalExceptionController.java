package org.example.princess_group.global.controller;

import org.example.princess_group.global.dto.RootResponse;
import org.example.princess_group.global.error.ErrorCode;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleException(ServiceException ex) {
        ErrorCode code = ex.getErrorCode();
        RootResponse<Object> response = RootResponse.builder()
            .status(code.code())
            .msg(code.message())
            .build();
        return ResponseEntity.status(code.status())
            .body(response);
    }
}
