package org.example.princess_group.domain.comment.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CommentErrorCode {
    ErrorCode NOT_VALID_LIST = new ErrorCode(HttpStatus.BAD_REQUEST, "3001", "유효하지 않은 리스트입니다.");
    ErrorCode NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, "3002", "존재하지 않은 댓글입니다.");
    ErrorCode NOT_VALID_USER = new ErrorCode(HttpStatus.NOT_FOUND, "3003", "유효하지 않은 유저입니다.");
}
