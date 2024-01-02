package org.example.princess_group.domain.card.dto;

public record CreateCardRequest(
    String name,
    Long listId,
    Long boardId
) {

}
