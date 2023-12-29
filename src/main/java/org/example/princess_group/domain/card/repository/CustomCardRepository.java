package org.example.princess_group.domain.card.repository;

import java.util.List;
import org.example.princess_group.domain.card.dto.ReadCardResponse;
import org.example.princess_group.domain.card.dto.ReadCardsRequest;
import org.example.princess_group.domain.card.entity.Card;

public interface CustomCardRepository {

    List<ReadCardResponse> findByCondition(ReadCardsRequest request);

    void changeOrder(Card card, Integer target);
}
