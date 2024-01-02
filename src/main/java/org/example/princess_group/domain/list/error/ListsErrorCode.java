package org.example.princess_group.domain.list.error;


import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface ListsErrorCode {

    ErrorCode NOT_EXIST_LIST = new ErrorCode(HttpStatus.BAD_REQUEST, "3000", "리스트가 없습니다.");
    ErrorCode LAST_ORDER = new ErrorCode(HttpStatus.BAD_REQUEST, "3001", "마지막 순서 입니다.");
    ErrorCode NOT_EXIST_NUMBER = new ErrorCode(HttpStatus.BAD_REQUEST, "3002", "존재 할수 없는 순서 입니다.");
}