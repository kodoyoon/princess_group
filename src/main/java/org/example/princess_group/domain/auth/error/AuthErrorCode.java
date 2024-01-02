package org.example.princess_group.domain.auth.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface AuthErrorCode {

    ErrorCode NOT_VALID_DOMAIN_ID = new ErrorCode(HttpStatus.BAD_REQUEST, "1000",
        "유효하지 않은 아이디입니다.");
    ErrorCode NOT_VALID_USER = new ErrorCode(HttpStatus.BAD_REQUEST, "1001", "유효하지 않은 유저입니다.");
}
