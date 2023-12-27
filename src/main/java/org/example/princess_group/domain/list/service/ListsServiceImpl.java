package org.example.princess_group.domain.list.service;

import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_LIST;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.repository.ListsRepository;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListsServiceImpl implements ListsService {

    private final ListsRepository repository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @Override
    public List<ReadListsResponse> getlists(Long id) {
        boardService.boardCheck(id);
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
}
