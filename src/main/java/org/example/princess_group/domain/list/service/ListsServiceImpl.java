package org.example.princess_group.domain.list.service;

import static org.example.princess_group.domain.board.error.BoardErrorCode.NOT_EXIST_BOARD;
import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_LIST;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.response.CreateListsResponse;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.repository.ListsRepository;
import org.example.princess_group.global.exception.ServiceException;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Target.ForField.ReadOnly;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListsServiceImpl implements ListsService {

    private final ListsRepository repository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    @Override
    public List<ReadListsResponse> getlists(Long id) {

        if(!boardService.boardCheck(id)){
            throw new ServiceException(NOT_EXIST_LIST);
        };

        Optional<Board> board = boardRepository.findById(id);
        List<Lists> lists = repository.findAllByBoard(board.get());

        if(lists.isEmpty()){
            throw new ServiceException(NOT_EXIST_LIST);
        }else{
            return lists.stream().map(l -> new ReadListsResponse(
                l.getId(),
                l.getName(),
                l.getOrder())).collect(Collectors.toList());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public CreateListsResponse createLists(Long id, CreateListsRequest request) {
        if(!boardService.boardCheck(id)){
            throw new ServiceException(NOT_EXIST_LIST);
        }
        Board board = boardRepository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_BOARD)
        );
        long order =  repository.count();
        Lists lists = new Lists(board,request,(int)(order + 1));
        Lists response = repository.save(lists);
        return CreateListsResponse.builder()
            .id(response.getId())
            .name(response.getName())
            .boardId(response.getBoard().getId())
            .build();
    }

    @Override
    @Transactional
    public CreateListsResponse updateLists(Long id, CreateListsRequest request) {
        Lists lists = repository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        lists.update(request);
        return CreateListsResponse.builder()
            .id(lists.getId())
            .name(lists.getName())
            .boardId(lists.getBoard().getId())
            .build();
    }

    @Override
    @Transactional
    public void deleteLists(Long id) {
        Lists lists = repository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        Long order = repository.orderFind(id);
        List<Lists> list =repository.orderChange(order);
        if(list.isEmpty()){
            repository.delete(lists);
        }else{
            for (Lists l : list){
                l.updateOrderDelete();
            }
            repository.delete(lists);
        }
    }
}
