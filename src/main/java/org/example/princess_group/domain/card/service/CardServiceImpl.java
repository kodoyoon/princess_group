package org.example.princess_group.domain.card.service;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.AllocateWorkerRequest;
import org.example.princess_group.domain.card.dto.AllocatedWorkerResponse;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.entity.Worker;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.list.service.ListService;
import org.example.princess_group.domain.user.service.UserServiceInterface;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final ListService listService;
    private final UserServiceInterface userService;

    public CreateCardResponse createCard(CreateCardRequest request) {

        // validation
        var isValidListId = listService.isValidId(request.listId());
        if (!isValidListId) {
            throw new ServiceException(CardErrorCode.NOT_VALID_LIST);
        }

        // logic
        Card entity = repository.saveAndFlush(Card.builder()
            .name(request.name())
            .listId(request.listId())
            .build());

        return CreateCardResponse.builder()
            .cardId(entity.getId())
            .listId(entity.getListId())
            .build();
    }

    @Transactional
    public UpdateCardResponse updateCard(UpdateCardRequest request) {

        // logic
        Card card = repository.findFetchById(request.cardId())
            .orElseThrow(() -> new ServiceException(CardErrorCode.NOT_FOUND));

        card.update(request);
        AllocatedWorkerResponse newWorker = allocateWorker(card, request.allocateWorker());

        return UpdateCardResponse.builder()
            .newWorker(newWorker)
            .cardId(card.getId())
            .name(request.name())
            .color(request.color())
            .deadline(request.deadLine())
            .description(request.description())
            .build();
    }

    private AllocatedWorkerResponse allocateWorker(Card card, AllocateWorkerRequest request) {

        // validation
        if(request == null) return null;

        boolean isValidUserId = userService.isValidUserId(request.userId());
        if(!isValidUserId){
            throw new ServiceException(CardErrorCode.NOT_VALID_USER);
        }

        // logic
        Worker worker = Worker.builder()
            .userId(request.userId())
            .card(card)
            .build();

        card.addWorker(worker);

        return AllocatedWorkerResponse.builder()
            .userId(worker.getUserId())
            .build();
    }

    public void deleteCard(Long cardId) {

    }

    public ChangeOrderResponse changeOrder(Long cardId, ChangeOrderRequest request) {
        return null;
    }
}
