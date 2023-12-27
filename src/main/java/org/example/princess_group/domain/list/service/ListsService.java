package org.example.princess_group.domain.list.service;

import java.util.List;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;

public interface ListsService {
    List<ReadListsResponse> getlists(Long id);
}
