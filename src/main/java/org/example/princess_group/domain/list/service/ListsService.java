package org.example.princess_group.domain.list.service;

import java.util.List;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.request.OrderChangeListsRequest;
import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;

public interface ListsService {

    boolean isValidId(Long listId);

    List<ReadListsResponse> getlists(Long id);

    void createLists(Long id, CreateListsRequest request);

    UpdateListsResponse updateLists(Long id, CreateListsRequest request);

    void deleteLists(Long id);

    List<ReadListsResponse> orderChangeLists(Long id, OrderChangeListsRequest request);
}
