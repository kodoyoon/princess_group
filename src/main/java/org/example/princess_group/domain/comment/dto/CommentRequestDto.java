package org.example.princess_group.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long cardId;
    @NotBlank
    private String content;
}
