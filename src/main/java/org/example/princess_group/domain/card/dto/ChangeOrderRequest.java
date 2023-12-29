package org.example.princess_group.domain.card.dto;

public record ChangeOrderRequest(
    Integer number,
    Long targetListId,
    Long boardId,
    Long listId
) {

}
