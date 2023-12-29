package org.example.princess_group.global.validation;

import static org.assertj.core.api.BDDAssertions.then;
import static org.example.princess_group.global.entity.ServiceAuthority.CREATE_CARD_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.CREATE_CARD_AT_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.CREATE_COMMENT;
import static org.example.princess_group.global.entity.ServiceAuthority.CREATE_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_CARD;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_CARD_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_CARD_AT_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_COMMENT;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_COMMENT_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_LIST_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.INVITE_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.ROLE_BOARD_ADMIN;
import static org.example.princess_group.global.entity.ServiceAuthority.ROLE_LIST_ADMIN;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_AT_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_IN_ORDER_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_IN_ORDER_AT_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_LIST_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_LIST_IN_ORDER;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.example.princess_group.domain.auth.entity.Auth;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.entity.DomainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AuthValidatorTest {

    AuthValidator validator = new AuthValidator();

    static Stream<Arguments> whenHasSameAuth() {
        return Stream.of(
            // 보드 권한
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(ROLE_BOARD_ADMIN).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(INVITE_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(DELETE_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(UPDATE_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(CREATE_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(UPDATE_LIST_IN_ORDER).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(UPDATE_LIST_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(DELETE_LIST_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(CREATE_CARD_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(UPDATE_CARD_IN_ORDER_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(UPDATE_CARD_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(DELETE_COMMENT_AT_BOARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(CREATE_COMMENT).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                .authority(DELETE_COMMENT_AT_BOARD).build()))),

            // 리스트 권한
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(UPDATE_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(DELETE_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(DELETE_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(ROLE_LIST_ADMIN).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(CREATE_CARD_AT_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(UPDATE_CARD_IN_ORDER_AT_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(UPDATE_CARD_AT_LIST).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.LIST)
                .authority(DELETE_CARD_AT_LIST).build()))),

            // 카드 권한
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.CARD)
                .authority(UPDATE_CARD).build()))),
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.CARD)
                .authority(DELETE_CARD).build()))),

            // 댓글 권한
            arguments(AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.COMMENT)
                .authority(DELETE_COMMENT).build())))
        );
    }

    static Stream<Arguments> whenHasSuperAuth() {
        return Stream.of(
            // 보드 전체 리스트의 업데이트 권한이 있다면, 리스트 업데이트 권한을 가진다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(UPDATE_LIST_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(UPDATE_LIST).build())),
                1L,
                2L
            ),
            // 보드 전체 리스트의 삭제 권한이 있다면, 리스트 삭제 권한을 가진다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(DELETE_LIST_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(DELETE_LIST).build())),
                1L,
                2L
            ),
            // 보드 전체 카드 업데이트 권한이 있다면, 카드 삭제 권한을 가진다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(UPDATE_CARD_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(UPDATE_CARD_AT_LIST).build())),
                1L,
                2L
            ),
            // 보드 전체에서 카드를 생성할 권한이 있다면, 어떤 리스트에서든 카드를 생성할 권한이 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(CREATE_CARD_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(CREATE_CARD_AT_LIST).build())),
                1L,
                2L
            ),
            // 보드 전체에서 카드 순서를 변경할 권한이 있다면, 하나의 리스트에서 순서를 변경할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(UPDATE_CARD_IN_ORDER_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(UPDATE_CARD_IN_ORDER_AT_LIST).build())),
                1L,
                2L
            ),
            // 보드 전체에서 카드를 수정할 권한이 있다면, 보드에 속한 모든 카드를 수정할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(UPDATE_CARD_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(3L).type(DomainType.CARD)
                    .authority(UPDATE_CARD).build())),
                1L,
                2L
            ),
            // 특정 리스트의 카드를 수정할 권한이 있다면, 그 리스트에 속한 모든 카드를 수정할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(UPDATE_CARD_AT_LIST).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(3L).type(DomainType.CARD)
                    .authority(UPDATE_CARD).build())),
                1L,
                2L
            ),
            // 보드 전체에서 카드를 삭제할 권한이 있다면, 보드에 속한 모든 카드를 삭제할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(DELETE_CARD_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(3L).type(DomainType.CARD)
                    .authority(DELETE_CARD).build())),
                1L,
                2L
            ),
            // 특정 리스트의 카드를 삭제할 권한이 있다면, 그 리스트에 속한 모든 카드를 삭제할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(2L).type(DomainType.LIST)
                    .authority(DELETE_CARD_AT_LIST).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(3L).type(DomainType.CARD)
                    .authority(DELETE_CARD).build())),
                1L,
                2L
            ),
            // 보드 전체에서 카드를 삭제할 권한이 있다면, 보드에 속한 모든 카드를 삭제할 수 있다.
            arguments(
                AuthInfo.of((Auth.builder().userId(1L).domainId(1L).type(DomainType.BOARD)
                    .authority(DELETE_COMMENT_AT_BOARD).build())),
                AuthInfo.of((Auth.builder().userId(1L).domainId(3L).type(DomainType.CARD)
                    .authority(DELETE_COMMENT).build())),
                1L,
                2L
            )
        );
    }

    @DisplayName("권한이 존재할 때 true")
    @ParameterizedTest
    @MethodSource("whenHasSameAuth")
    void whenExistAuth(AuthInfo auth) {
        // given
        var userAuths = List.of(auth);
        // when
        boolean result = validator.validate(userAuths, auth, 1L, null);
        // then
        then(result).isTrue();
    }

    @DisplayName("권한이 존재하지 않을 때 true")
    @ParameterizedTest
    @MethodSource("whenHasSameAuth")
    void whenNotExistAuth(AuthInfo auth) {
        // given
        List<AuthInfo> userAuths = List.of();
        // when
        boolean result = validator.validate(userAuths, auth, 1L, null);
        // then
        then(result).isFalse();
    }

    @DisplayName("상위 권한이 존재할 때 true")
    @ParameterizedTest
    @MethodSource("whenHasSuperAuth")
    void whenExistSuperAuth(AuthInfo userAuth, AuthInfo auth, Long boardId, Long listId) {
        // given
        var userAuths = List.of(userAuth);
        // when
        boolean result = validator.validate(userAuths, auth, boardId, listId);
        // then
        then(result).isTrue();
    }

    @DisplayName("상위 권한이 존재하지 않을 때 false")
    @ParameterizedTest
    @MethodSource("whenHasSuperAuth")
    void whenNotExistSuperAuth(AuthInfo userAuth, AuthInfo auth, Long boardId, Long listId) {
        // given
        List<AuthInfo> userAuths = List.of();
        // when
        boolean result = validator.validate(userAuths, auth, boardId, listId);
        // then
        then(result).isFalse();
    }
}