package org.example.princess_group.global.exception;

import lombok.Getter;
import org.example.princess_group.global.error.ErrorCode;

@Getter
public class ServiceException extends RuntimeException{
    private ErrorCode errorCode;
    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
