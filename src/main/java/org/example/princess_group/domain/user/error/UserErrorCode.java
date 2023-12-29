package org.example.princess_group.domain.user.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface UserErrorCode {
    ErrorCode NOT_EXIST_USER= new ErrorCode(HttpStatus.BAD_REQUEST, "1000", "사용자가 없습니다.");
    ErrorCode CHECK_USER = new ErrorCode(HttpStatus.BAD_REQUEST, "1001", "중복된 유저 입니다.");
    ErrorCode CHECK_ID_PASSWORD = new ErrorCode(HttpStatus.BAD_REQUEST, "1002", "아이디와 비밀번호를 확인해주세요.");

    ErrorCode NOT_LOGIN = new ErrorCode(HttpStatus.BAD_REQUEST, "9000", "세션이 없습니다.");
}
