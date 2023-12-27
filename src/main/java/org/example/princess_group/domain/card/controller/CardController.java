package org.example.princess_group.domain.card.controller;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.CreateCardRequest;
import org.example.princess_group.domain.card.dto.CreateCardResponse;
import org.example.princess_group.domain.card.dto.DeleteCardResponse;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.domain.card.dto.UpdateCardResponse;
import org.example.princess_group.domain.card.service.CardService;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RootResponse<?> createCard(@RequestBody CreateCardRequest body) {
        CreateCardResponse response = cardService.createCard(body);
        return RootResponse.builder()
            .status(HttpStatus.CREATED.name())
            .msg("카드 생성 성공했습니다.")
            .data(response)
            .build();
    }

    @PatchMapping
    public RootResponse<?> updateCard(@RequestBody UpdateCardRequest body) {
        UpdateCardResponse response = cardService.updateCard(body);
        return RootResponse.builder()
            .status(HttpStatus.OK.name())
            .msg("카드 수정 성공했습니다.")
            .data(response)
            .build();
    }

    @DeleteMapping("/{cardId}")
    public RootResponse<?> deleteCard(@PathVariable("cardId") Long cardId) {
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
    public RootResponse<?> changeOrder(@PathVariable("cardId") Long cardId) {
        return null;
    }
}
