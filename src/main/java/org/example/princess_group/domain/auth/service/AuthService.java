package org.example.princess_group.domain.auth.service;

import org.example.princess_group.domain.auth.entity.DomainType;
import org.example.princess_group.domain.auth.entity.ServiceAuthority;

public interface AuthService {

    boolean hasAuthority(Long userId, DomainType type, ServiceAuthority authority, Long domainId);

    boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority,
        Long domainId);
}
