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

    public boolean hasAuthority(AuthInfo info) {
        return userId.equals(info.userId) &&
            domainId.equals(info.domainId) &&
            type.equals(info.type) &&
            authority.equals(info.authority);
    }

    public boolean hasAuthority(AuthInfo info, Long boardId, Long listId) {
        if(hasAuthority(info)) return true;

        Long targetId = this.type == DomainType.BOARD ? boardId : listId;
        return userId.equals(info.userId) &&
            containsAuth(info.authority, targetId);
    }

    private boolean containsAuth(ServiceAuthority auth, Long targetId) {
        return authority.contains(auth, domainId, targetId);
    }
}