package org.example.princess_group.domain.card.dto;

import lombok.Builder;

@Builder
public record ChangeOrderResponse(
    Long cardId,
    Integer number
) {

}
