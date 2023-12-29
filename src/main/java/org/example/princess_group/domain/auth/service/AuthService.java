package org.example.princess_group.domain.auth.service;

import java.util.List;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;

public interface AuthService {

    /**
     * 유저에게 권한을 부여하는 기능을 제공한다.
     * 사전에 권한을 부여하는 요청자(로그인 유저)가 권한 부여 권한이 있는지 확인하고 해당 메서드를 이용해야한다.
     * @param userId 권한을 부여할 대상 유저의 id
     * @param type 권한 도메인 타입
     * @param authority 권한 타입
     * @param domainId 도메인 id
     * @return 성공시 true, 실패시 false
     */
    boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority,
        Long domainId);

    List<AuthInfo> getAuthorityByUserId(Long userId);
}
