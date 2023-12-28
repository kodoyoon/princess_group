package org.example.princess_group.domain.card.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.example.princess_group.domain.card.entity.Card;

@Builder
public record ReadCardResponse(
    Long cardId,
    Long listId,
    LocalDateTime modifiedAt,
    String name,
    String description,
    String color,
    LocalDateTime deadline,
    Integer order,
    List<WorkerInfo> workers
) {

    public static ReadCardResponse of(Card card) {
        return new ReadCardResponse(
            card.getId(),
            card.getListId(),
            card.getModifiedAt(),
            card.getName(),
            card.getDescription(),
            card.getColor(),
            card.getDeadline(),
            card.getOrder(),
            card.getWorkers().stream().map(WorkerInfo::of).toList()
        );
    }

}
