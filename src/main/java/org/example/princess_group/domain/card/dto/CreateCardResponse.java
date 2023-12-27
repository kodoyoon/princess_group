package org.example.princess_group.domain.card.dto;

import lombok.Builder;

@Builder
public record CreateCardResponse(
    Long cardId
) {
}
