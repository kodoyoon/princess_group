package org.example.princess_group.domain.list.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.repository.ListsRepository;
import org.example.princess_group.global.error.ErrorCode;
import org.example.princess_group.global.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ListsServiceTest {

    @Mock
    ListsRepository repository;
    @Mock
    BoardRepository boardRepository;
    @Mock
    BoardService boardService;

    ListsService listsService;

    @BeforeEach
    void init(){
        this.listsService = new ListsServiceImpl(repository,boardService);
    }

    @DisplayName("리스트 조회")
    @Nested
    class getLists{

        @DisplayName("리스트 조회 성공")
        @Test
        void success(){

            List<Lists> list= new ArrayList<>();
            Lists lists1 = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            Lists lists2 = Lists.builder().boardId(2L).name("두번째").order(2L).build();
            list.add(lists1);
            list.add(lists2);
            //given
            var boardId = 1L;
            given(boardService.isValidId(boardId)).willReturn(Boolean.TRUE);
            given(repository.findAllByBoardId(1L)).willReturn(list);
            //when
            List<ReadListsResponse> responses = listsService.getlists(boardId);
            //then
            assertThat(responses.get(0).name()).isEqualTo(lists1.getName());
            assertThat(responses.get(0).order()).isEqualTo(lists1.getOrder());
            assertThat(responses.get(1).name()).isEqualTo(lists2.getName());
            assertThat(responses.get(1).order()).isEqualTo(lists2.getOrder());
        }

        @DisplayName("리스트 조회 실패")
        @Test
        void fail_1(){

            List<Lists> lists= new ArrayList<>();
            Lists lists1 = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            Lists lists2 = Lists.builder().boardId(2L).name("두번째").order(2L).build();
            lists.add(lists1);
            lists.add(lists2);
            //given
            var boardId = 1L;
            given(boardService.isValidId(boardId)).willReturn(Boolean.FALSE);
            //when

            //then
            assertThatThrownBy(()->listsService.getlists(boardId))
                .isInstanceOf(ServiceException.class)
                .satisfies(exception->{
                    ErrorCode errorCode =  ((ServiceException) exception).getErrorCode();
                    assertThat(errorCode.code()).isEqualTo("3000");
                    assertThat(errorCode.message()).isEqualTo("리스트가 없습니다.");
                    //"3000", "리스트가 없습니다."
                });
        }


        @DisplayName("리스트 조회 실패")
        @Test
        void fail_2(){

            List<Lists> list= new ArrayList<>();
            //given
            var boardId = 1L;
            given(boardService.isValidId(boardId)).willReturn(Boolean.TRUE);
            given(repository.findAllByBoardId(1L)).willReturn(list);
            //when

            //then
            assertThatThrownBy(()->listsService.getlists(boardId))
                .isInstanceOf(ServiceException.class)
                .satisfies(exception->{
                    ErrorCode errorCode =  ((ServiceException) exception).getErrorCode();
                    assertThat(errorCode.code()).isEqualTo("3000");
                    assertThat(errorCode.message()).isEqualTo("리스트가 없습니다.");
                    //"3000", "리스트가 없습니다."
                });
        }
    }

    @DisplayName("리스트 생성")
    @Nested
    class createLists{
        @DisplayName("리스트 생성 성공")
        @Test
        void success(){
            //given
            var boardId = 1L;
            CreateListsRequest request = new CreateListsRequest("첫번째");

            given(boardService.isValidId(boardId)).willReturn(Boolean.TRUE);
            given(repository.countByBoardId(1L)).willReturn(0L);
            //when
            listsService.createLists(boardId,request);
            //then
            then(repository).should().save(any(Lists.class));
        }

        @DisplayName("리스트 생성 실패")
        @Test
        void fail_2(){

            CreateListsRequest request = new CreateListsRequest("첫번째");
            //given
            var boardId = 1L;

            given(boardService.isValidId(boardId)).willReturn(Boolean.FALSE);
            //when

            //then
            assertThatThrownBy(()->listsService.createLists(boardId,request))
                .isInstanceOf(ServiceException.class)
                .satisfies(exception->{
                    ErrorCode errorCode =  ((ServiceException) exception).getErrorCode();
                    assertThat(errorCode.code()).isEqualTo("3000");
                    assertThat(errorCode.message()).isEqualTo("리스트가 없습니다.");
                    //"3000", "리스트가 없습니다."
                });
        }
    }
    @DisplayName("리스트 업데이트")
    @Nested
    class updateLists{
        @DisplayName("리스트 업데이트 성공")
        @Test
        void success(){
            //given
            Lists lists = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            ReflectionTestUtils.setField(lists,"id",1L);
            var boardId = 1L;
            CreateListsRequest request = new CreateListsRequest("첫번째 수정");

            given(repository.findById(1L)).willReturn(Optional.of(lists));
            //when
            UpdateListsResponse response = listsService.updateLists(boardId,request);
            //then
            assertThat(response.name()).isEqualTo(request.name());
        }

        @DisplayName("리스트 업데이트 실패")
        @Test
        void fail(){
            //given
            Lists lists = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            ReflectionTestUtils.setField(lists,"id",1L);
            var boardId = 1L;
            CreateListsRequest request = new CreateListsRequest("첫번째 수정");
            //when

            //then
            assertThatThrownBy(()->listsService.updateLists(lists.getId(),request))
                .isInstanceOf(ServiceException.class)
                .satisfies(exception->{
                    ErrorCode errorCode =  ((ServiceException) exception).getErrorCode();
                    assertThat(errorCode.code()).isEqualTo("3000");
                    assertThat(errorCode.message()).isEqualTo("리스트가 없습니다.");
                    //"3000", "리스트가 없습니다."
                });
        }
    }

    @DisplayName("리스트 삭제")
    @Nested
    class deleteLists{
        @DisplayName("리스트 삭제 성공")
        @Test
        void success(){
            //given
            Lists lists = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            ReflectionTestUtils.setField(lists,"id",1L);
            var boardId = 1L;

            given(repository.findById(1L)).willReturn(Optional.of(lists));
            //when
            listsService.deleteLists(boardId);
            //then
            then(repository).should().delete(lists);
        }
        @DisplayName("리스트 삭제 실패")
        @Test
        void fail_1(){
            //given
            Lists lists = Lists.builder().boardId(1L).name("첫번째").order(1L).build();
            ReflectionTestUtils.setField(lists,"id",1L);
            var boardId = 1L;
            //when
            //then
            assertThatThrownBy(()->listsService.deleteLists(1L))
                .isInstanceOf(ServiceException.class)
                .satisfies(exception->{
                    ErrorCode errorCode =  ((ServiceException) exception).getErrorCode();
                    assertThat(errorCode.code()).isEqualTo("3000");
                    assertThat(errorCode.message()).isEqualTo("리스트가 없습니다.");
                    //"3000", "리스트가 없습니다."
                });;
        }
    }
}