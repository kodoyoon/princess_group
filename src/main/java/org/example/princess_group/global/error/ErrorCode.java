package org.example.princess_group.global.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorCode(
    HttpStatus status,
    String code,
    String message) {
}
