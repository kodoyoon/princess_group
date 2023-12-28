package org.example.princess_group.domain.auth.service;

import java.util.List;
import org.example.princess_group.domain.auth.dto.AuthInfo;
import org.example.princess_group.domain.auth.entity.DomainType;
import org.example.princess_group.domain.auth.entity.ServiceAuthority;

public class AuthServiceImpl implements AuthService {

    @Override
    public boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority, Long domainId) {
        return false;
    }

    @Override
    public List<AuthInfo> getAuthorityByUserId(Long userId) {
        return null;
    }
}
