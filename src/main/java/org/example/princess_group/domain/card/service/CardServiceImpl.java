package org.example.princess_group.domain.card.service;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.AllocateWorkerRequest;
import org.example.princess_group.domain.card.dto.AllocatedWorkerResponse;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.DeleteWorkerRequest;
import org.example.princess_group.domain.card.dto.DeleteWorkerResponse;
import org.example.princess_group.domain.card.dto.ListCardInfo;
import org.example.princess_group.domain.card.dto.ReadCardResponse;
import org.example.princess_group.domain.card.dto.ReadCardsRequest;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.dto.WorkerInfo;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.entity.Worker;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.card.repository.WorkerRepository;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.domain.user.service.UserServiceInterface;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final WorkerRepository workerRepository;
    private final ListsService listService;
    private final UserServiceInterface userService;

    public CreateCardResponse createCard(CreateCardRequest request) {

        // validation
        var isValidListId = listService.isValidId(request.listId());
        if (!isValidListId) {
            throw new ServiceException(CardErrorCode.NOT_VALID_LIST);
        }

        int count = repository.countByListId(request.listId()).intValue();
        // logic
        Card entity = repository.saveAndFlush(Card.builder()
            .name(request.name())
            .order(count)
            .listId(request.listId())
            .build());

        return CreateCardResponse.builder()
            .cardId(entity.getId())
            .listId(entity.getListId())
            .order(entity.getOrder())
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
        repository.deleteById(cardId);
    }

    @Transactional
    public ChangeOrderResponse changeOrder(Long cardId, ChangeOrderRequest request) {

        // validation
        if (!repository.existsByIdAndListId(cardId, request.targetListId())) {
            throw new ServiceException(CardErrorCode.NOT_FOUND);
        }

        // logic
        Card card = repository.findById(cardId)
            .orElseThrow(() -> new ServiceException(CardErrorCode.NOT_FOUND));

        repository.changeOrder(card, request);

        return ChangeOrderResponse.builder()
            .number(card.getOrder())
            .cardId(card.getId())
            .build();
    }

    public ReadCardResponse readCard(Long cardId) {
        Card card = repository.findFetchById(cardId)
            .orElseThrow(() -> new ServiceException(CardErrorCode.NOT_FOUND));

        return ReadCardResponse.builder()
            .cardId(card.getId())
            .listId(card.getListId())
            .workers(card.getWorkers().stream().map(WorkerInfo::of).toList())
            .modifiedAt(card.getModifiedAt())
            .name(card.getName())
            .description(card.getDescription())
            .deadline(card.getDeadline())
            .color(card.getColor())
            .order(card.getOrder())
            .build();
    }

    public List<ListCardInfo> readCards(ReadCardsRequest request) {

        List<ListCardInfo> result = new ArrayList<>();

        Map<Long, List<ReadCardResponse>> data = repository
            .findByCondition(request).stream()
            .collect(groupingBy(ReadCardResponse::listId));

        for (Long listId : data.keySet()) {
            result.add(new ListCardInfo(listId, data.get(listId)));
        }

        return result;
    }

    @Override
    public boolean isValidId(Long listId) {
        return false;
    }
}
