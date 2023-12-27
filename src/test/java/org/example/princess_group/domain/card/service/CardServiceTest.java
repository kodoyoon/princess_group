package org.example.princess_group.domain.card.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.repository.CardRepository;
import org.example.princess_group.domain.list.service.ListService;
import org.example.princess_group.global.error.ErrorCode;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.suppport.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Card Service Test")
class CardServiceTest extends RepositoryTest {

    @Autowired
    CardRepository cardRepository;
    CardServiceImpl cardService;
    ListService listService = mock(ListService.class);

    @BeforeEach
    void init() {
        if (cardService == null) {
            cardService = new CardServiceImpl(cardRepository, listService);
        }
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
}