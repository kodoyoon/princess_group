package org.example.princess_group.domain.board.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface BoardErrorCode {
    ErrorCode NOT_EXIST_BOARD = new ErrorCode(HttpStatus.BAD_REQUEST, "2000", "보드가 없습니다.");
}
