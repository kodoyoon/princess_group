package org.example.princess_group.domain.card.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ReadCardResponse(
    Long cardId,
    LocalDateTime modifiedAt,
    String name,
    String description,
    String color,
    LocalDateTime deadline,
    Integer order,
    List<WorkerInfo> workers
) {

}
