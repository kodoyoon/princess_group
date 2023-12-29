package org.example.princess_group.global.dto;

import lombok.Builder;
import org.example.princess_group.domain.auth.entity.Auth;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;

@Builder
public record AuthInfo(
    Long userId,
    ServiceAuthority authority,
    DomainType type,
    Long domainId
) {

    public static AuthInfo of(Auth auth) {
        return AuthInfo.builder()
            .authority(auth.getAuthority())
            .type(auth.getType())
            .domainId(auth.getDomainId())
            .userId(auth.getUserId())
            .build();
    }

    public boolean hasAuthority(AuthInfo info){
        return hasAuthority(info.userId, info.type, info.authority, info.domainId);
    }

    public boolean hasAuthority(Long userId, DomainType type, ServiceAuthority authority,
        Long domainId) {
        return this.userId.equals(userId) &&
            this.type.equals(type) &&
            this.authority.equals(authority) &&
            this.domainId.equals(domainId);
    }
}