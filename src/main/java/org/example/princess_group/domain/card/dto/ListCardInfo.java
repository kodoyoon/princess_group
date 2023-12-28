package org.example.princess_group.domain.card.dto;

import java.util.List;

public record ListCardInfo(
    Long listId,
    List<ReadCardResponse> cards
) {

}
