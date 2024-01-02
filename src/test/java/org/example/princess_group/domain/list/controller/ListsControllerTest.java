package org.example.princess_group.domain.list.controller;

import static org.example.princess_group.domain.list.error.ListsErrorCode.LAST_ORDER;
import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_LIST;
import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.request.OrderChangeListsRequest;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;
import org.example.princess_group.domain.user.dto.CreateUserRequest;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.suppport.ControllerTest;
import org.example.princess_group.suppport.MockSpringSecurityFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@DisplayName("Lists API 테스트")
class ListsControllerTest extends ControllerTest {

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
    @Nested
    @DisplayName("list 조회")
    class getList {

        @DisplayName("리스트 조회 성공")
        @Test
        void get_lists_success() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 1L);
            var response2 = new ReadListsResponse(2L, "두번째", 2L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            given(listsService.getlists(1L)).willReturn(list);
            //when

            //then
            mockMvc.perform(get("/api/lists/1").session(session))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("리스트 조회에 성공했습니다.")
                );
        }

        @DisplayName("리스트 조회 실패")
        @Test
        void get_lists_fail() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 1L);
            var response2 = new ReadListsResponse(2L, "두번째", 2L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            given(listsService.getlists(1L)).willThrow(new ServiceException(NOT_EXIST_LIST));
            //when

            //then
            mockMvc.perform(get("/api/lists/1").session(session))
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }
    }

    @Nested
    @DisplayName("list 생성")
    class createList {

        @DisplayName("리스트 생성 성공")
        @Test
        void create_lists_success() throws Exception {
            //given

            CreateListsRequest request = new CreateListsRequest("첫번째");
            doNothing().when(listsService).createLists(1L, request);
            //when
            //then
            mockMvc.perform(post("/api/lists/1").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("리스트 저장에 성공했습니다.")
                );
        }


        @DisplayName("리스트 생성 실패")
        @Test
        void create_lists_fail_1() throws Exception {
            //given
            CreateListsRequest request = new CreateListsRequest("첫번째");
            //when
            doThrow(new ServiceException(NOT_EXIST_LIST)).when(listsService)
                .createLists(1L, request);
            //then
            mockMvc.perform(post("/api/lists/1").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }
    }

    @Nested
    @DisplayName("list 업데이트")
    class updateList {

        @DisplayName("리스트 업데이트 성공")
        @Test
        void update_success() throws Exception {
            //given
            var listsId = 1L;
            CreateListsRequest request = new CreateListsRequest("첫번째 수정");
            UpdateListsResponse response = new UpdateListsResponse(1L, 1L, "첫번째 수정");
            given(listsService.updateLists(listsId, request)).willReturn(response);
            //when

            //then
            mockMvc.perform(patch("/api/lists/1").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("리스트 수정에 성공했습니다.")
                );
        }

        @DisplayName("리스트 업데이트 실패")
        @Test
        void update_fail_1() throws Exception {
            //given

            CreateListsRequest request = new CreateListsRequest("첫번째");
            given(listsService.updateLists(1L, request)).willThrow(
                new ServiceException(NOT_EXIST_LIST));
            //when

            //then
            mockMvc.perform(patch("/api/lists/1").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }
    }

    @Nested
    @DisplayName("list 삭제")
    class deleteList {

        @DisplayName("리스트 삭제 성공")
        @Test
        void update_success() throws Exception {
            //given
            doNothing().when(listsService).deleteLists(1L);
            //when

            //then
            mockMvc.perform(delete("/api/lists/1").session(session))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("리스트 삭제에 성공했습니다.")
                );
        }

        @DisplayName("리스트 삭제 실패")
        @Test
        void update_fail() throws Exception {
            //given
            doThrow(new ServiceException(NOT_EXIST_LIST)).when(listsService)
                .deleteLists(1L);
            //when

            //then
            mockMvc.perform(delete("/api/lists/1").session(session))
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }
    }
    @Nested
    @DisplayName("list 순서 변경")
    class changeOrderList {
        @DisplayName("리스트 삭제 성공")
        @Test
        void order_change_success() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 2L);
            var response2 = new ReadListsResponse(2L, "두번째", 1L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            OrderChangeListsRequest request = OrderChangeListsRequest.builder().number(1L).build();
            given(listsService.orderChangeLists(2L,request)).willReturn(list);
            //when

            //then
            mockMvc.perform(put("/api/lists/2").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("순서 변경에 성공했습니다.")
                );
        }


        @DisplayName("리스트 삭제 실패1")
        @Test
        void order_change_fail() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 2L);
            var response2 = new ReadListsResponse(2L, "두번째", 1L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            OrderChangeListsRequest request = OrderChangeListsRequest.builder().number(1L).build();
            given(listsService.orderChangeLists(2L,request)).willThrow(
                new ServiceException(NOT_EXIST_LIST));
            //when

            //then
            mockMvc.perform(put("/api/lists/2").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }

        @DisplayName("리스트 삭제 실패2")
        @Test
        void order_change_fail2() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 2L);
            var response2 = new ReadListsResponse(2L, "두번째", 1L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            OrderChangeListsRequest request = OrderChangeListsRequest.builder().number(1L).build();
            given(listsService.orderChangeLists(2L,request)).willThrow(
                new ServiceException(LAST_ORDER));
            //when

            //then
            mockMvc.perform(put("/api/lists/2").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3001"),
                    jsonPath("$.msg").value("마지막 순서 입니다.")
                );
        }

        @DisplayName("리스트 삭제 실패2")
        @Test
        void order_change_fail3() throws Exception {
            //given
            var response = new ReadListsResponse(1L, "첫번째", 2L);
            var response2 = new ReadListsResponse(2L, "두번째", 1L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            OrderChangeListsRequest request = OrderChangeListsRequest.builder().number(1L).build();
            given(listsService.orderChangeLists(7L,request)).willThrow(
                new ServiceException(NOT_EXIST_NUMBER));
            //when

            //then
            mockMvc.perform(put("/api/lists/7").session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3002"),
                    jsonPath("$.msg").value("존재 할수 없는 순서 입니다.")
                );
        }

    }
}