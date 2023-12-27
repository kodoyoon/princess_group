package org.example.princess_group.domain.card.service;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.list.service.ListService;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ListService listService;

    public CreateCardResponse createCard(CreateCardRequest request) {
        var isValidListId = listService.isValidId(request.listId());
        if (!isValidListId) {
            throw new ServiceException(CardErrorCode.NOT_VALID_LIST);
        }

        Card entity = cardRepository.saveAndFlush(Card.builder()
            .name(request.name())
            .listId(request.listId())
            .build());

        return CreateCardResponse.builder()
            .cardId(entity.getId())
            .listId(entity.getListId())
            .build();
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
