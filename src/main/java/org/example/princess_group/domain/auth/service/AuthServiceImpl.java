package org.example.princess_group.domain.auth.service;

import org.example.princess_group.domain.auth.entity.DomainType;
import org.example.princess_group.domain.auth.entity.ServiceAuthority;

public class AuthServiceImpl implements AuthService {

    @Override
    public boolean hasAuthority(Long userId, DomainType type, ServiceAuthority authority, Long domainId) {
        return false;
    }

    @Override
    public boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority, Long domainId) {
        return false;
    }
}
