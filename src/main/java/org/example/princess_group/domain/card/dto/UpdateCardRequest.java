package org.example.princess_group.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
public record UpdateCardRequest(
    Long cardId,
    String name,
    String description,
    String color,
    LocalDateTime deadLine
) {

}
