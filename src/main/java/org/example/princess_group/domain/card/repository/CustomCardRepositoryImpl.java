package org.example.princess_group.domain.card.repository;

import static org.example.princess_group.domain.card.entity.QCard.card;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.dto.ChangeOrderRequest;
import org.example.princess_group.domain.card.dto.ReadCardResponse;
import org.example.princess_group.domain.card.dto.ReadCardsRequest;
import org.example.princess_group.domain.card.entity.Card;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CustomCardRepositoryImpl implements CustomCardRepository {

    private final EntityManager em;
    private JPAQueryFactory query;

    @PostConstruct
    private void init() {
        query = new JPAQueryFactory(em);
    }

    @Override
    public List<ReadCardResponse> findByCondition(ReadCardsRequest request) {
        return query.selectFrom(card)
            .where(card.listId.in(request.listIds()))
            .fetch()
            .stream()
            .map(ReadCardResponse::of)
            .toList();
    }

    @Transactional
    @Override
    public void changeOrder(Card card, ChangeOrderRequest req) {
        // validation
        if (card.getOrder().equals(req.number())) {
            return;
        }

        // logic
        em.merge(card);
        if (card.getListId().equals(req.listId())) {
            changeOrderWhenNotChangeList(card, req);
        } else {
            changeOrderWhenChangeList(card, req);
        }
        em.flush();
        em.clear();
    }

    /**
     * 카드가 다른 리스트로 이동할 때, 카드가 있던 리스트에서는 이동한 카드의 뒤 순서에 존재하는 카드의 순서를 -1한다.
     * 이동할 리스트에서는 카드 순서보다 같거나 큰 카드의 순서를 +1 한다.
     * @param card 이동할 카드
     * @param req 카드의 순서 이동 정보
     */
    private void changeOrderWhenChangeList(Card card, ChangeOrderRequest req) {
        pushCardOrderAtList(req.listId(), req.number(), Integer.MAX_VALUE);
        pullCardOrderAtList(card.getListId(), card.getOrder(), Integer.MAX_VALUE);
        card.setOrder(req.number());
    }

    private void changeOrderWhenNotChangeList(Card card, ChangeOrderRequest req) {
        if (card.getOrder() < req.number()) {
            pullCardOrderAtList(card.getListId(), card.getOrder() + 1, req.number());
        } else {
            pushCardOrderAtList(card.getListId(), req.number(), card.getOrder() - 1);
        }
        card.setOrder(req.number());
    }

    private void pushCardOrderAtList(Long listId, Integer from, Integer to) {
        query
            .update(card)
            .set(card.order, card.order.add(1))
            .where(
                card.listId.eq(listId),
                card.order.between(from, to)
            )
            .execute();
    }

    private void pullCardOrderAtList(Long listId, Integer from, Integer to) {
        query
            .update(card)
            .set(card.order, card.order.add(-1))
            .where(
                card.listId.eq(listId),
                card.order.between(from, to)
            )
            .execute();
    }
}
