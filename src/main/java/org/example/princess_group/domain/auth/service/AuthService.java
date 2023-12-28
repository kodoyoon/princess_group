package org.example.princess_group.domain.auth.service;

import org.example.princess_group.domain.auth.entity.DomainType;

public interface AuthService {

    boolean hasAuthority(DomainType type, ServiceAuthority authority);

}
