package org.example.princess_group.domain.card.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CardErrorCode {

    ErrorCode NOT_VALID_LIST = new ErrorCode(HttpStatus.BAD_REQUEST, "3001", "유효하지 않은 리스트입니다.");
    ErrorCode NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, "3002", "요청한 데이터를 찾을 수 없습니다.");
    ErrorCode NOT_VALID_USER = new ErrorCode(HttpStatus.NOT_FOUND, "3003", "유효하지 않은 유저입니다.");
    ErrorCode NOT_HAVE_AUTH = new ErrorCode(HttpStatus.FORBIDDEN, "3004", "권한이 없습니다.");
}
