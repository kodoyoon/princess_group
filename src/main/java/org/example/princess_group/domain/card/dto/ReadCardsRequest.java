package org.example.princess_group.domain.card.dto;

import java.util.List;

public record ReadCardsRequest(
    List<Long> listIds
) {

}
