package org.example.princess_group.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder
public record RootResponse<T>(
    String status,
    String msg,
    T data
) {

}