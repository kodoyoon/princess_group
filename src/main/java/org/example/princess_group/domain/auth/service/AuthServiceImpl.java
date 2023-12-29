package org.example.princess_group.domain.auth.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.auth.repository.AuthRepository;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Override
    public boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority, Long domainId) {
        return false;
    }

    @Override
    public List<AuthInfo> getAuthorityByUserId(Long userId) {
        return authRepository.findAllByUserId(userId).stream()
            .map(AuthInfo::of)
            .toList();
    }
}
