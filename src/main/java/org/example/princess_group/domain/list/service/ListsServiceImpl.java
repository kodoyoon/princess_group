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
import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;
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
    public boolean isValidId(Long listId) {
        Lists lists = repository.findById(listId).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        return true;
    }

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
    @Transactional
    public void createLists(Long id, CreateListsRequest request) {
        if (!boardService.boardCheck(id)) {
            throw new ServiceException(NOT_EXIST_LIST);
        }
        long order = repository.countByBoardId(id);

        Lists lists = Lists.builder().boardId(id).name(request.name()).order((order+1)).build();
        Lists response = repository.save(lists);
    }

    @Override
    @Transactional
    public UpdateListsResponse updateLists(Long id, CreateListsRequest request) {
        Lists lists = repository.findById(id).orElseThrow(
            () -> new ServiceException(NOT_EXIST_LIST)
        );
        lists.update(request);
        return UpdateListsResponse.builder()
            .id(id)
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
        List<Lists> list = repository.orderChangeDelete(lists.getBoardId(), order);
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
        if (request.number() > order) {
            throw new ServiceException(NOT_EXIST_NUMBER);
        }else if(lists.getId()<request.number()){
            List<Lists> list = repository.orderChangeUpdate(lists.getBoardId(),request.number());
            for (Lists l : list) {
                l.updateOrderDelete();
            }
            lists.updateOrder(request);
        }else if(lists.getId().equals(request.number())){
            throw new ServiceException(LAST_ORDER);
        }else{
            List<Lists> list = repository.orderChangeUpdate(lists.getBoardId(),request.number());
            for (Lists l : list) {
                l.updateOrderChange();
            }
            lists.updateOrder(request);
        }
        List<Lists> response = repository.findAllByBoardId(lists.getBoardId());
        return response.stream().map(l -> new ReadListsResponse(
            l.getId(),
            l.getName(),
            l.getOrder())).collect(Collectors.toList());
    }
}
