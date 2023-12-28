package org.example.princess_group.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseEntity {

    @Column(nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceAuthority authority;
    @Enumerated(EnumType.STRING)
    private DomainType type;
    private Long domainId;

    @Builder
    private Auth(Long userId, ServiceAuthority authority, DomainType type, Long domainId) {
        this.userId = userId;
        this.authority = authority;
        this.type = type;
        this.domainId = domainId;
    }
}
