package org.example.princess_group.global.validation;

import org.example.princess_group.global.entity.ServiceAuthority;

/**
 * 권한의 계층 관계를 표시하기 위한 함수 인터페이스입니다.
 * 호출하는 대상이 상위 권한임을 가정하였습니다.
 */
public interface RelationExpression {

    /**
     * 상위 권한의 포함관계를 판단해주는 메서드입니다.
     * @param auth 하위 권한 후보
     * @param selfId 상위 권한의 id
     * @param targetId 하위 권한이 속한 상위 권한의 id (ex board(id=1) vs board(id=2)
     * @return 포함 여부를 반환합니다.
     */
    boolean contains(ServiceAuthority auth, Long selfId, Long targetId);
}