package org.example.princess_group.domain.card.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CardErrorCode {
    ErrorCode NOT_VALID_LIST = new ErrorCode(HttpStatus.BAD_REQUEST, "3001", "유효하지 않은 리스트입니다.");
}
