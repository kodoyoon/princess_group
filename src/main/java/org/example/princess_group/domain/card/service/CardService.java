package org.example.princess_group.domain.card.service;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public CreateCardResponse createCard(CreateCardRequest request) {
        return null;
    }

    public UpdateCardResponse updateCard(UpdateCardRequest request) {
        return null;
    }

    public void deleteCard(Long cardId) {

    }

    public ChangeOrderResponse changeOrder(Long cardId, ChangeOrderRequest request) {
        return null;
    }
}
