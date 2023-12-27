package org.example.princess_group.domain.card.service;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.AllocateWorkerRequest;
import org.example.princess_group.domain.card.dto.AllocatedWorkerResponse;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.DeleteWorkerRequest;
import org.example.princess_group.domain.card.dto.DeleteWorkerResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.entity.Worker;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.card.repository.WorkerRepository;
import org.example.princess_group.domain.list.service.ListService;
import org.example.princess_group.domain.user.service.UserServiceInterface;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final WorkerRepository workerRepository;
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
        DeleteWorkerResponse deleteWorkerResponse = deleteWorker(card, request.deleteWorker());

        return UpdateCardResponse.builder()
            .newWorker(newWorker)
            .removeWorker(deleteWorkerResponse)
            .cardId(card.getId())
            .name(request.name())
            .color(request.color())
            .deadline(request.deadLine())
            .description(request.description())
            .build();
    }

    private DeleteWorkerResponse deleteWorker(Card card, DeleteWorkerRequest request) {

        // validation
        if (request == null) {
            return null;
        }

        Long userId = request.userId();
        validateUserId(userId);

        // logic
        card.removeWorker(userId);

        return DeleteWorkerResponse.builder()
            .userId(userId)
            .build();
    }

    private AllocatedWorkerResponse allocateWorker(Card card, AllocateWorkerRequest request) {

        // validation
        if (request == null) {
            return null;
        }

        Long userId = request.userId();
        validateUserId(userId);

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

    private void validateUserId(Long userId) {
        if (!userService.isValidUserId(userId)) {
            throw new ServiceException(CardErrorCode.NOT_VALID_USER);
        }
    }

    @Transactional
    public void deleteCard(Long cardId) {
        workerRepository.deleteByCardId(cardId);
    }

    public ChangeOrderResponse changeOrder(Long cardId, ChangeOrderRequest request) {
        return null;
    }
}
