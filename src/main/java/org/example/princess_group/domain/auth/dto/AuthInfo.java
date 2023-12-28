package org.example.princess_group.domain.auth.dto;

import lombok.Builder;
import org.example.princess_group.domain.auth.entity.DomainType;
import org.example.princess_group.domain.auth.entity.ServiceAuthority;

@Builder
public record AuthInfo(
    Long userId,
    ServiceAuthority authority,
    DomainType type,
    Long domainId
) {

}
