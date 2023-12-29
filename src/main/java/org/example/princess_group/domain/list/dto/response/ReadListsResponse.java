package org.example.princess_group.domain.list.dto.response;

import lombok.Builder;

public record ReadListsResponse(
    Long id,
    String name,
    Long order
) {
}
