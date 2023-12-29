package org.example.princess_group.domain.auth.service;

import static org.example.princess_group.global.entity.DomainType.BOARD;
import static org.example.princess_group.global.entity.DomainType.CARD;
import static org.example.princess_group.global.entity.DomainType.COMMENT;
import static org.example.princess_group.global.entity.DomainType.LIST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.auth.entity.Auth;
import org.example.princess_group.domain.auth.error.AuthErrorCode;
import org.example.princess_group.domain.auth.repository.AuthRepository;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.card.service.CardService;
import org.example.princess_group.domain.comment.service.CommentService;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.domain.user.service.UserService;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final CardService cardService;
    private final ListsService listService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userServiceInterface;

    @Override
    public boolean assignAuthority(Long userId, DomainType type, ServiceAuthority authority,
        Long domainId) {
        // validation
        // 유효한 유저가 아닌 경우
        if (userServiceInterface.isValidUserId(userId)) {
            throw new ServiceException(AuthErrorCode.NOT_VALID_USER);
        }
        // 존재하지 않은 domainId 인경우
        if ((type.equals(BOARD) && !boardService.isValidId(domainId)) ||
            (type.equals(LIST) && !listService.isValidId(domainId)) ||
            (type.equals(CARD) && !cardService.isValidId(domainId)) ||
            (type.equals(COMMENT) && !commentService.isValidId(domainId))
        ) {
            throw new ServiceException(AuthErrorCode.NOT_VALID_DOMAIN_ID);
        }

        // logic
        authRepository.save(Auth.builder()
            .authority(authority)
            .domainId(domainId)
            .type(type)
            .userId(userId)
            .build());

        return true;
    }

    @Override
    public List<AuthInfo> getAuthorityByUserId(Long userId) {
        return authRepository.findAllByUserId(userId).stream()
            .map(AuthInfo::of)
            .toList();
    }
}
