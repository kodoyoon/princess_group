package org.example.princess_group.domain.auth.service;

import org.example.princess_group.domain.auth.entity.DomainType;

public class AuthServiceImpl implements AuthService {

    @Override
    public boolean hasAuthority(DomainType type, ServiceAuthority authority) {
        return false;
    }
}
