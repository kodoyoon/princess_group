package org.example.princess_group.domain.auth.dto;

import lombok.Getter;
import org.example.princess_group.domain.auth.entity.Auth;
import org.example.princess_group.domain.auth.entity.Authority;
import org.example.princess_group.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class AuthResponseDto {
    private Long id;
    private User user;
    private Authority authority;
    private String type;
    private Long domainId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AuthResponseDto(Auth auth) {
        this.id = auth.getId();
        this.user = auth.getUser();
        this.authority = auth.getAuthority();
        this.type = auth.getType();
        this.domainId = auth.getDomainId();
        this.createdAt = auth.getCreatedAt();
        this.modifiedAt = auth.getModifiedAt();
    }
}
