package org.example.princess_group.domain.card.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.example.princess_group.domain.card.dto.AllocateWorkerRequest;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.DeleteWorkerRequest;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.entity.Worker;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.card.repository.WorkerRepository;
import org.example.princess_group.domain.list.service.ListService;
import org.example.princess_group.domain.user.service.UserServiceInterface;
import org.example.princess_group.global.error.ErrorCode;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.suppport.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@DisplayName("Card Service Test")
class CardServiceTest extends RepositoryTest {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    WorkerRepository workerRepository;
    CardServiceImpl cardService;
    UserServiceInterface userService = mock(UserServiceInterface.class);
    ListService listService = mock(ListService.class);

    @BeforeEach
    void init() {
        if (cardService == null) {
            cardService = new CardServiceImpl(cardRepository, workerRepository, listService,
                userService);
        }
        cardRepository.deleteAllInBatch();
    }

    @DisplayName("카드 생성")
    @Nested
    class CreateCard {

        @DisplayName("카드 생성시 리스트(칼럼) 정보가 필요하다.")
        @Nested
        class MustNeedList {

            @DisplayName("유효하지 않은 리스트인 경우 카드 생성 실패")
            @Test
            void when_not_exist_list() {
                // given
                var notValidListId = 2L;
                given(listService.isValidId(notValidListId)).willReturn(false);

                var request = new CreateCardRequest("test", notValidListId);
                // when // then
                thenThrownBy(() -> cardService.createCard(request))
                    .isInstanceOf(ServiceException.class)
                    .satisfies()
                    .satisfies(ex -> {
                        ErrorCode errorCode = ((ServiceException) ex).getErrorCode();
                        then(errorCode).isEqualTo(CardErrorCode.NOT_VALID_LIST);
                    });
            }

            @DisplayName("칼럼 정보가 있는 경우 카드 생성 성공")
            @Test
            void when_exist_list() {
                // given
                var validListId = 1L;
                var request = new CreateCardRequest("test", validListId);
                given(listService.isValidId(validListId)).willReturn(true);

                // when
                CreateCardResponse response = cardService.createCard(request);
                // then
                then(response.cardId()).isNotNull();
                then(response.listId()).isEqualTo(request.listId());
            }
        }
    }

    @DisplayName("카드 수정")
    @Nested
    class UpdateCard {

        @DisplayName("이름 변경 성공")
        @Test
        void change_name_success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("before name")
                .build());

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .name("change name")
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.name()).isEqualTo(request.name());
        }

        @DisplayName("설명 변경 성공")
        @Test
        void change_description_success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .description("before description")
                .build());

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .description("change description")
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.description()).isEqualTo(request.description());
        }

        @DisplayName("색상 변경 성공")
        @Test
        void change_color_success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .color("before color")
                .build());

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .color("change color")
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.color()).isEqualTo(request.color());
        }

        @DisplayName("마감일 변경 성공")
        @Test
        void change_deadline_success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build());

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .deadLine(LocalDateTime.of(2001, 1, 1, 1, 1, 1))
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.deadline()).isEqualTo(request.deadLine());
        }

        @DisplayName("작업자 할당 성공")
        @Test
        void allocate_worker_to_card_success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build());

            var validUserId = 10L;
            given(userService.isValidUserId(validUserId)).willReturn(true);
            var allocateWorkerRequest = new AllocateWorkerRequest(validUserId);

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .allocateWorker(allocateWorkerRequest)
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.newWorker().userId()).isEqualTo(validUserId);
        }

        @DisplayName("유효하지않은 사용자인 경우 작업자 할당 실패")
        @Test
        void when_userId_is_not_valid_then_allocate_worker_to_card_fail() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build());

            var inValidUserId = 10L;
            given(userService.isValidUserId(inValidUserId)).willReturn(false);
            var allocateWorkerRequest = new AllocateWorkerRequest(inValidUserId);

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .allocateWorker(allocateWorkerRequest)
                .build();
            // when // then
            thenThrownBy(() -> cardService.updateCard(request))
                .isInstanceOf(ServiceException.class)
                .satisfies(ex -> {
                    ErrorCode errorCode = ((ServiceException) ex).getErrorCode();
                    then(errorCode).isEqualTo(CardErrorCode.NOT_VALID_USER);
                });
        }

        @DisplayName("유효하지않은 사용자인 경우 작업자 삭제 실패")
        @Test
        void when_userId_is_not_valid_then_delete_worker_fail() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build());

            var invalidUserId = 10L;
            given(userService.isValidUserId(invalidUserId)).willReturn(false);
            var deleteWorkerRequest = new DeleteWorkerRequest(invalidUserId);

            var request = UpdateCardRequest.builder()
                .cardId(card.getId())
                .deleteWorker(deleteWorkerRequest)
                .build();
            // when // then
            thenThrownBy(() -> cardService.updateCard(request))
                .isInstanceOf(ServiceException.class)
                .satisfies(ex -> {
                    ErrorCode errorCode = ((ServiceException) ex).getErrorCode();
                    then(errorCode).isEqualTo(CardErrorCode.NOT_VALID_USER);
                });
        }

        @DisplayName("작업자 삭제 성공")
        @Test
        void delete_worker_success() {
            // given
            var card = Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build();

            var worker = Worker.builder()
                .card(card)
                .userId(11L)
                .build();

            card.addWorker(worker);
            Card cardEntity = cardRepository.saveAndFlush(card);

            var validUserId = 10L;
            given(userService.isValidUserId(validUserId)).willReturn(true);

            var request = UpdateCardRequest.builder()
                .cardId(cardEntity.getId())
                .deleteWorker(new DeleteWorkerRequest(validUserId))
                .build();
            // when
            UpdateCardResponse response = cardService.updateCard(request);
            // then
            then(response.removeWorker().userId()).isEqualTo(validUserId);
        }
    }

    @DisplayName("카드 삭제")
    @Nested
    class DeleteCard {

        @DisplayName("성공")
        @Test
        void success() {
            // given
            var card = cardRepository.saveAndFlush(Card.builder()
                .listId(1L)
                .name("sample")
                .deadline(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build());

            var targetCardId = card.getId();
            // when
            cardService.deleteCard(targetCardId);
            // then
            then(workerRepository.existsById(targetCardId)).isFalse();
        }
    }
}