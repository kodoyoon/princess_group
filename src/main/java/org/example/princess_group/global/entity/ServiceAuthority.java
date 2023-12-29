package org.example.princess_group.global.entity;

import java.util.Objects;
import org.example.princess_group.global.validation.RelationExpression;

public enum ServiceAuthority {

    // 보드 초대 권한, domainId=boardId
    INVITE_BOARD,
    // 보드 삭제 권한, domainId=boardId
    DELETE_BOARD,
    // 보드 정보 수정 권한, domainId=boardId
    UPDATE_BOARD,
    // 보드 관리자 권한(권한 부여 가능), domainId=boardId
    ROLE_BOARD_ADMIN,


    // 리스트 생성 권한, domainId=boardId
    CREATE_LIST,
    // 하나의 리스트 수정 권한, domainId=listId
    UPDATE_LIST,
    // 특정 보드에서 리스트 순서 변경 권한, domainId=boardId
    UPDATE_LIST_IN_ORDER,
    // 특정 보드에 존재하는 리스트 수정 권한, domainId=boardId
    UPDATE_LIST_AT_BOARD( (auth, selfId, targetId) -> auth == UPDATE_LIST && Objects.equals(selfId, targetId)),
    // 특정 리스트 삭제 권한, domainId=listId
    DELETE_LIST,
    // 특정 보드에 존재하는 리스트 삭제 권한, domainId=boardId
    DELETE_LIST_AT_BOARD( (auth, selfId, targetId) -> auth == DELETE_LIST && Objects.equals(selfId, targetId)),
    // 리스트 관리자 권한(리스트 권한 부여 가능), domainId=listId
    ROLE_LIST_ADMIN,
    // 특정 리스트에서 카드 생성 가능한 권한, domainId=listId
    CREATE_CARD_AT_LIST,
    // 어떤 리스트이든지 보드에서 카드 생성 가능한 권한, domainId=boardId
    CREATE_CARD_AT_BOARD( (auth, selfId, targetId) -> auth == CREATE_CARD_AT_LIST && Objects.equals(selfId, targetId)),
    // 하나의 카드 수정 권한, domainId=cardId
    UPDATE_CARD,
    // 리스트에 카드 순서 변경 권한, domainId=listId, 해당 권한이 없는 리스트에는 카드 순서 변경이 제한됨
    UPDATE_CARD_IN_ORDER_AT_LIST,
    // 보드에서 카드 순서 변경 권한, domainId=boardId
    UPDATE_CARD_IN_ORDER_AT_BOARD( (auth, selfId, targetId) -> auth == UPDATE_CARD_IN_ORDER_AT_LIST && Objects.equals(selfId, targetId)),
    // 리스트에 존재하는 카드 수정 권한, domainId=listId
    UPDATE_CARD_AT_LIST( (auth, selfId, targetId) -> auth == UPDATE_CARD && Objects.equals(selfId, targetId)),
    // 보드에 존재하는 카드 수정 권한, domainId=boardId
    UPDATE_CARD_AT_BOARD( (auth, selfId, targetId) ->
        (auth == UPDATE_CARD_AT_LIST || auth == UPDATE_CARD) && Objects.equals(selfId, targetId)),
    // 특정 카드 삭제 권한, domainId=cardId
    DELETE_CARD,
    // 특정 리스트에 존재하는 카드 삭제 권한, domainId=listId
    DELETE_CARD_AT_LIST( (auth, selfId, targetId) -> auth == DELETE_CARD && Objects.equals(selfId, targetId)),
    // 특정 보드에 존재하는 카드 삭제 권한, domainId=boardId
    DELETE_CARD_AT_BOARD( (auth, selfId, targetId) -> auth == DELETE_CARD && Objects.equals(selfId, targetId)),

    // 댓글 생성 권한, domainId=boardId
    CREATE_COMMENT,
    // 하나의 댓글 삭제 권한, domainId=commentId
    DELETE_COMMENT,
    // 보드에 존재하는 댓글 삭제 권한, domainId=boardId
    DELETE_COMMENT_AT_BOARD( (auth, selfId, targetId) -> auth == DELETE_COMMENT && Objects.equals(selfId, targetId));

    private final RelationExpression expression;

    ServiceAuthority() {
        expression = (auth, selfId, targetId) -> false;
    }

    ServiceAuthority(RelationExpression expression) {
        this.expression = expression;
    }

    public boolean contains(ServiceAuthority auth, Long selfId, Long targetId) {
        return expression.contains(auth, selfId, targetId);
    }
}
