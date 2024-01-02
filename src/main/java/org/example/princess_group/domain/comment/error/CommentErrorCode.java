package org.example.princess_group.domain.comment.error;

import org.example.princess_group.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CommentErrorCode {
    ErrorCode NOT_EXIST_COMMENT = new ErrorCode(HttpStatus.BAD_REQUEST, "5000", "댓글이 없습니다.");
}
