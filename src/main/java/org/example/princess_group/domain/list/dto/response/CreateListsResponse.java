package org.example.princess_group.domain.list.dto.response;

import lombok.Builder;

@Builder
public record CreateListsResponse(
    Long id,
    Long boardId,
    String name

) {

}
