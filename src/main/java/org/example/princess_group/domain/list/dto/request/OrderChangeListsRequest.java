package org.example.princess_group.domain.list.dto.request;

import lombok.Builder;

@Builder
public record OrderChangeListsRequest(
    Long number
) {

}
