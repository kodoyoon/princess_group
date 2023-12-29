package org.example.princess_group.domain.auth.service;

import java.util.List;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;

public interface AuthService {

    boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority,
        Long domainId);

    List<AuthInfo> getAuthorityByUserId(Long userId);
}
