package org.example.princess_group.domain.list.dto.request;

import lombok.Getter;

@Getter
public record CreateListsRequest(
    String name
) {

}
