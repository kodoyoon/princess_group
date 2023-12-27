package org.example.princess_group.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder
public record UpdateCardResponse(
    Long cardId,
    String name,
    String description,
    String color,
    LocalDateTime deadline,
    AllocatedWorkerResponse newWorker
) {

}
