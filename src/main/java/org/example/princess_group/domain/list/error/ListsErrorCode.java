package org.example.princess_group.domain.list.error;


import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface ListsErrorCode {
    ErrorCode NOT_EXIST_LIST = new ErrorCode(HttpStatus.BAD_REQUEST, "3000", "리스트가 없습니다.");
}