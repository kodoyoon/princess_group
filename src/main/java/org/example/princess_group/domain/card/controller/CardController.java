package org.example.princess_group.domain.card.controller;

import static org.example.princess_group.global.entity.ServiceAuthority.CREATE_CARD_AT_LIST;
import static org.example.princess_group.global.entity.ServiceAuthority.DELETE_CARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_IN_ORDER_AT_BOARD;
import static org.example.princess_group.global.entity.ServiceAuthority.UPDATE_CARD_IN_ORDER_AT_LIST;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.auth.service.AuthService;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ChangeOrderResponse;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.DeleteCardResponse;
import org.example.princess_group.domain.card.dto.ReadCardResponse;
import org.example.princess_group.domain.card.dto.ReadCardsRequest;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.error.CardErrorCode;
import org.example.princess_group.domain.card.service.CardServiceImpl;
import org.example.princess_group.global.dto.AuthInfo;
import org.example.princess_group.global.dto.RootResponse;
import org.example.princess_group.global.entity.DomainType;
import org.example.princess_group.global.entity.ServiceAuthority;
import org.example.princess_group.global.exception.ServiceException;
import org.example.princess_group.global.validation.AuthValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardServiceImpl cardService;
    private final AuthValidator authValidator;
    private final AuthService authService;
    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RootResponse<?> createCard(
        HttpServletRequest request,
        @RequestBody CreateCardRequest body
    ) {

        Long loginUserId = getLoginUserId(request);

        List<AuthInfo> userAuths = authService.getAuthorityByUserId(loginUserId);
        AuthInfo needAuth = AuthInfo.builder()
            .userId(loginUserId)
            .authority(CREATE_CARD_AT_LIST)
            .type(DomainType.LIST)
            .domainId(body.listId())
            .build();

        if (!authValidator.validate(userAuths, needAuth, body.boardId(), body.listId())) {
            throw new ServiceException(CardErrorCode.NOT_HAVE_AUTH);
        }

        CreateCardResponse response = cardService.createCard(body);
        return RootResponse.builder()
            .status(HttpStatus.CREATED.name())
            .msg("카드 생성 성공했습니다.")
            .data(response)
            .build();
    }

    @PatchMapping
    public RootResponse<?> updateCard(
        HttpServletRequest request,
        @RequestBody UpdateCardRequest body
    ) {
        Long loginUserId = getLoginUserId(request);

        List<AuthInfo> userAuths = authService.getAuthorityByUserId(loginUserId);
        AuthInfo needAuth = AuthInfo.builder()
            .userId(loginUserId)
            .authority(UPDATE_CARD)
            .type(DomainType.CARD)
            .domainId(body.cardId())
            .build();

        if (!authValidator.validate(userAuths, needAuth, body.boardId(), body.listId())) {
            throw new ServiceException(CardErrorCode.NOT_HAVE_AUTH);
        }

        UpdateCardResponse response = cardService.updateCard(body);
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 수정 성공했습니다.")
            .data(response)
            .build();
    }

    @DeleteMapping("/{cardId}")
    public RootResponse<?> deleteCard(
        HttpServletRequest request,
        @PathVariable("cardId") Long cardId,
        @RequestParam("listId") Long listId,
        @RequestParam("boardId") Long boardId
    ) {
        Long loginUserId = getLoginUserId(request);

        List<AuthInfo> userAuths = authService.getAuthorityByUserId(loginUserId);
        AuthInfo needAuth = AuthInfo.builder()
            .userId(loginUserId)
            .authority(DELETE_CARD)
            .type(DomainType.CARD)
            .domainId(cardId)
            .build();

        if (!authValidator.validate(userAuths, needAuth, boardId, listId)) {
            throw new ServiceException(CardErrorCode.NOT_HAVE_AUTH);
        }

        cardService.deleteCard(cardId);
        DeleteCardResponse response = DeleteCardResponse.builder()
            .cardId(cardId)
            .build();
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 삭제 성공했습니다.")
            .data(response)
            .build();
    }

    @PostMapping("/{cardId}")
    public RootResponse<?> changeOrder(
        HttpServletRequest request,
        @PathVariable("cardId") Long cardId,
        @RequestBody ChangeOrderRequest body
    ) {
        Long loginUserId = getLoginUserId(request);

        List<AuthInfo> userAuths = authService.getAuthorityByUserId(loginUserId);
        ServiceAuthority authority = null;
        DomainType type = null;
        Long targetId = null;

        if (body.targetListId().equals(body.listId())) {
            authority = UPDATE_CARD_IN_ORDER_AT_LIST;
            type = DomainType.LIST;
            targetId = body.listId();
        } else {
            authority = UPDATE_CARD_IN_ORDER_AT_BOARD;
            type = DomainType.BOARD;
            targetId = body.boardId();
        }

        AuthInfo needAuth = AuthInfo.builder()
            .userId(loginUserId)
            .authority(authority)
            .type(type)
            .domainId(targetId)
            .build();

        if (!authValidator.validate(userAuths, needAuth, body.boardId(), body.targetListId())) {
            throw new ServiceException(CardErrorCode.NOT_HAVE_AUTH);
        }

        ChangeOrderResponse response = cardService.changeOrder(cardId, body);
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 이동 성공했습니다.")
            .data(response)
            .build();
    }

    @GetMapping("/{cardId}")
    public RootResponse<?> getCard(@PathVariable("cardId") Long cardId) {
        ReadCardResponse response = cardService.readCard(cardId);
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 상세 조회 성공했습니다.")
            .data(response)
            .build();
    }

    @GetMapping
    public RootResponse<?> getCards(@ModelAttribute ReadCardsRequest request) {
        var response = cardService.readCards(request);
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 목록 조회 성공했습니다.")
            .data(response)
            .build();
    }

    private Long getLoginUserId(HttpServletRequest request) {
        return null;
    }
}
