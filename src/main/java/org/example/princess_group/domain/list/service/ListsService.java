package org.example.princess_group.domain.list.service;

import java.util.List;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.response.CreateListsResponse;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;

public interface ListsService {

    List<ReadListsResponse> getlists(Long id);

    CreateListsResponse createLists(Long id, CreateListsRequest request);

    CreateListsResponse updateLists(Long id, CreateListsRequest request);

    void deleteLists(Long id);
}
