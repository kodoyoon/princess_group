package org.example.princess_group.domain.card.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.service.CardService;
import org.example.princess_group.suppport.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("카드 API : Controller Test")
class CardControllerTest extends ControllerTest {

    @MockBean
    CardService cardService;

    @DisplayName("카드 생성 API")
    @Nested
    class CreateCard {
        @DisplayName("성공 201")
        @Test
        void createCardApi() throws Exception {
            // given
            var body = new CreateCardRequest();
            var responseBody = CreateCardResponse.builder()
                .cardId(1L)
                .build();
            given(cardService.createCard(any()))
                .willReturn(responseBody);
            // when // then
            mockMvc.perform(post("/api/cards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(body))
                )
                .andDo(print())
                .andExpectAll(
                    status().isCreated(),
                    jsonPath("$.status").value(HttpStatus.CREATED.name()),
                    jsonPath("$.msg").value("카드 생성 성공했습니다."),
                    jsonPath("$.data.cardId").value(1L)
                );
        }
    }
}