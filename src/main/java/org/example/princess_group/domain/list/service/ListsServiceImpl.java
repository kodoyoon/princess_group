package org.example.princess_group.domain.list.service;

import static org.example.princess_group.domain.board.error.BoardErrorCode.NOT_EXIST_BOARD;
import static org.example.princess_group.domain.list.error.ListsErrorCode.LAST_ORDER;
import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_LIST;
import static org.example.princess_group.domain.list.error.ListsErrorCode.NOT_EXIST_NUMBER;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.request.OrderChangeListsRequest;
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

    @Override
    public List<ReadListsResponse> getlists(Long id) {

        if (!boardService.boardCheck(id)) {
            throw new ServiceException(NOT_EXIST_LIST);
        }
        List<Lists> lists = repository.findAllByBoardId(id);

        if (lists.isEmpty()) {
            throw new ServiceException(NOT_EXIST_LIST);
        } else {
            return lists.stream().map(l -> new ReadListsResponse(
                l.getId(),
                l.getName(),
                l.getOrder())).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CreateListsResponse createLists(Long id, CreateListsRequest request) {
        if (!boardService.boardCheck(id)) {
            throw new ServiceException(NOT_EXIST_LIST);
        }
        long order = repository.countByBoardId(id);
        Lists lists = Lists.builder().boardId(id).name(request.name()).order((order+1)).build();
        Lists response = repository.save(lists);
        return CreateListsResponse.builder()
            .id(response.getId())
            .name(response.getName())
            .boardId(response.getBoardId())
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
            .boardId(lists.getBoardId())
            .build();
    }

    @Override
    @Transactional
    public void deleteLists(Long id) {
        Lists lists = repository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        Long order = repository.orderFind(id);
        List<Lists> list = repository.orderChangeDelete(order);
        if (list.isEmpty()) {
            repository.delete(lists);
        } else {
            for (Lists l : list) {
                l.updateOrderDelete();
            }
            repository.delete(lists);
        }
    }

    @Override
    @Transactional
    public List<ReadListsResponse> orderChangeLists(Long id, OrderChangeListsRequest request) {
        Lists lists = repository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        Long order = repository.orderFind(id);
        List<Lists> list = repository.orderChangeUpdate(request.number());
        if (request.number() > order) {
            throw new ServiceException(NOT_EXIST_NUMBER);
        } else if (list.isEmpty()) {
            throw new ServiceException(LAST_ORDER);
        } else {
            for (Lists l : list) {
                l.updateOrderChange();
            }
            lists.updateOrder(request);
        }
        Board board = boardRepository.findById(lists.getBoardId()).orElseThrow(
            () -> new ServiceException(NOT_EXIST_BOARD)
        );
        List<Lists> response = repository.findAllByBoardId(lists.getBoardId());
        return response.stream().map(l -> new ReadListsResponse(
            l.getId(),
            l.getName(),
            l.getOrder())).collect(Collectors.toList());
    }
}
