package org.example.princess_group.global.validation;

import java.util.List;
import org.example.princess_group.global.dto.AuthInfo;
import org.springframework.stereotype.Service;

@Service
public class AuthValidator {

    /**
     * 권한 부여 전, 유저가 가진 권한으로 특정 권한을 생성할 수 있는지 판단한다.
     *
     * @param authInfos 권한을 부여할 유저의 권한 목록
     * @param auth      부여할 권한 정보
     * @param boardId   부여할 권한과 관련된 boardId
     * @param listId    부여할 권한과 관련된 targetListId, 없다면 null 입력한다.
     * @return 생성할 수 있는지 여부, 생성가능시 true, 불가능시 false
     */
    public boolean validate(List<AuthInfo> authInfos, AuthInfo auth, Long boardId,
        Long listId) {
        return authInfos.stream()
            .anyMatch(userAuth -> userAuth.hasAuthority(auth, boardId, listId));
    }
}
