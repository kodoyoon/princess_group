package org.example.princess_group.domain.list.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListsServiceImpl implements ListsService {

    private final ListsRepository repository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

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

    @Transactional
    public CreateListsResponse createLists(Long id, CreateListsRequest request) {
        if(!boardService.boardCheck(id)){
            throw new ServiceException(NOT_EXIST_LIST);
        };
        Optional<Board> board = boardRepository.findById(id);
        int order =  repository.countAll();
        Lists lists = new Lists(board.get(),request,order+1);
        var save = repository.save(lists);
        return CreateListsResponse.builder().id(save.getId()).name(save.getName()).boardId(board.get().getId()).build();
    }
}
