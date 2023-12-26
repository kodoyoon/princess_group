package org.example.princess_group.global.exception;

import org.example.princess_group.global.error.ErrorCode;

public class ServiceException extends RuntimeException{
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
