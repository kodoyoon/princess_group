package org.example.princess_group.domain.list.controller;

import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_LIST;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.repository.ListsRepository;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.suppport.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("Lists API 테스트")
class ListsControllerTest extends ControllerTest {
    @MockBean
    ListsService listsService;
    @MockBean
    ListsRepository repository;

    @Nested
    @DisplayName("list 조회")
    class ListsGet{
        @DisplayName("리스트 조회 성공")
        @Test
        void get_lists_success() throws  Exception{
            //given
            var response = new ReadListsResponse(1L,"첫번째",1L);
            var response2 = new ReadListsResponse(2L,"두번째",2L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            given(listsService.getlists(1L)).willReturn(list);
            //when

            //then
            mockMvc.perform(get("/api/lists/1"))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.status").value("200"),
                    jsonPath("$.msg").value("리스트 조회에 성공했습니다.")
                );
        }

        @DisplayName("리스트 조회 실패")
        @Test
        void get_lists_fail() throws  Exception{
            //given
            var response = new ReadListsResponse(1L,"첫번째",1L);
            var response2 = new ReadListsResponse(2L,"두번째",2L);
            List<ReadListsResponse> list = new ArrayList<>();
            list.add(response);
            list.add(response2);
            given(listsService.getlists(1L)).willThrow(new ServiceException(NOT_EXIST_LIST));
            //when

            //then
            mockMvc.perform(get("/api/lists/1"))
                .andDo(print())
                .andExpectAll(
                    status().isBadRequest(),
                    jsonPath("$.status").value("3000"),
                    jsonPath("$.msg").value("리스트가 없습니다.")
                );
        }
    }
}