package org.example.princess_group.domain.card.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.ListCardInfo;
import org.example.princess_group.domain.card.dto.ReadCardResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.suppport.ControllerTest;
import org.example.princess_group.suppport.MockSpringSecurityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DisplayName("카드 API : Controller Test")
class CardControllerTest extends ControllerTest {

    MockHttpSession session;
    MockHttpServletRequest  request;
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        String username = "123453";
        String password = "123453";
        CreateUserRequest req =new CreateUserRequest(username,password);
        session =new MockHttpSession();
        request = new MockHttpServletRequest();
        session.setAttribute("login_user",req);
        given(authValidator.validate(any(),any(),any(),any())).willReturn(Boolean.TRUE);
    }

    @DisplayName("카드 생성 API")
    @Nested
    class CreateCard {

        @DisplayName("성공 201")
        @Test
        void success() throws Exception {
            // given
            var body = new CreateCardRequest("test", 2L,1L);
            var responseBody = CreateCardResponse.builder()
                .cardId(1L)
                .listId(2L)
                .build();
            given(cardService.createCard(any()))
                .willReturn(responseBody);
            // when // then
            mockMvc.perform(post("/api/cards").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(body))
                )
                .andDo(print())
                .andExpectAll(
                    status().isCreated(),
                    jsonPath("$.status").value(HttpStatus.CREATED.name()),
                    jsonPath("$.msg").value("카드 생성 성공했습니다."),
                    jsonPath("$.data.cardId").value(1L),
                    jsonPath("$.data.listId").value(2L)
                );
        }
    }

    @DisplayName("카드 수정 API")
    @Nested
    class UpdateCard {

        @DisplayName("성공 200")
        @Test
        void success() throws Exception {
            // given
            var body = UpdateCardRequest.builder()
                .cardId(1L)
                .name("name")
                .description("description")
                .color("color")
                .deadLine(LocalDateTime.of(2000, 1, 1, 0, 0, 0))
                .build();

            var responseBody = UpdateCardResponse.builder()
                .cardId(1L)
                .build();
            given(cardService.updateCard(any()))
                .willReturn(responseBody);
            // when // then
            mockMvc.perform(patch("/api/cards").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(body))
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value(HttpStatus.OK.name()),
                    jsonPath("$.msg").value("카드 수정 성공했습니다."),
                    jsonPath("$.data.cardId").value(1L)
                );
        }
    }

    @DisplayName("카드 삭제 API")
    @Nested
    class DeleteCard {

        @DisplayName("성공 200")
        @Test
        void success() throws Exception {
            // given
            var cardId = 1L;
            // when // then
            mockMvc.perform(delete("/api/cards/{cardId}?listId=1&boardId=1", cardId).session(session))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value(HttpStatus.OK.name()),
                    jsonPath("$.msg").value("카드 삭제 성공했습니다."),
                    jsonPath("$.data.cardId").value(1L)
                );
        }
    }

    @DisplayName("카드 순서 변경 API")
    @Nested
    class ChangeOrder {

        @DisplayName("성공 200")
        @Test
        void success() throws Exception {
            // given
            var cardId = 1L;
            var listId = 2L;
            var number = 2;
            var requestBody = new ChangeOrderRequest(number, listId,1L,2L);
            var responseBody = ChangeOrderResponse.builder()
                .cardId(cardId)
                .number(number)
                .build();
            given(cardService.changeOrder(eq(cardId), any())).willReturn(responseBody);
            // when // then
            mockMvc.perform(post("/api/cards/{cardId}", cardId).session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestBody))
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value(HttpStatus.OK.name()),
                    jsonPath("$.msg").value("카드 이동 성공했습니다."),
                    jsonPath("$.data.cardId").value(1L),
                    jsonPath("$.data.number").value(number)
                );
        }
    }

    @DisplayName("카드 단건 조회 API")
    @Nested
    class ReadCard {

        @DisplayName("성공 200")
        @Test
        void success() throws Exception {
            // given
            var cardId = 1L;
            given(cardService.readCard(cardId))
                .willReturn(ReadCardResponse.builder()
                    .cardId(cardId)
                    .order(1)
                    .modifiedAt(LocalDateTime.now())
                    .color("blue")
                    .deadline(LocalDateTime.now())
                    .name("test")
                    .description("test description")
                    .workers(List.of())
                    .build());
            // when // then
            mockMvc.perform(get("/api/cards/{cardId}", cardId).session(session))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value(HttpStatus.OK.name()),
                    jsonPath("$.msg").value("카드 상세 조회 성공했습니다."),
                    jsonPath("$.data.cardId").value(cardId),
                    jsonPath("$.data.order").value(1),
                    jsonPath("$.data.modifiedAt").exists(),
                    jsonPath("$.data.color").value("blue"),
                    jsonPath("$.data.name").value("test"),
                    jsonPath("$.data.description").value("test description"),
                    jsonPath("$.data.deadline").exists(),
                    jsonPath("$.data.workers").exists()
                );
        }
    }

    @DisplayName("카드 목록 조회 API")
    @Nested
    class ReadCards {

        @DisplayName("성공 200")
        @Test
        void success() throws Exception {
            // given
            ReadCardResponse response = ReadCardResponse.builder()
                .cardId(1L)
                .listId(2L)
                .order(1)
                .modifiedAt(LocalDateTime.now())
                .color("blue")
                .deadline(LocalDateTime.now())
                .name("test")
                .description("test description")
                .workers(List.of())
                .build();

            given(cardService.readCards(any()))
                .willReturn(List.of(new ListCardInfo(2L, List.of(response))));
            // when // then
            mockMvc.perform(get("/api/cards").session(session))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value(HttpStatus.OK.name()),
                    jsonPath("$.msg").value("카드 목록 조회 성공했습니다."),
                    jsonPath("$.data[0].listId").value(2L),
                    jsonPath("$.data[0].cards[0].cardId").value(1L),
                    jsonPath("$.data[0].cards[0].order").value(1),
                    jsonPath("$.data[0].cards[0].modifiedAt").exists(),
                    jsonPath("$.data[0].cards[0].color").value("blue"),
                    jsonPath("$.data[0].cards[0].name").value("test"),
                    jsonPath("$.data[0].cards[0].description").value("test description"),
                    jsonPath("$.data[0].cards[0].deadline").exists(),
                    jsonPath("$.data[0].cards[0].workers").exists()
                );
        }
    }
}