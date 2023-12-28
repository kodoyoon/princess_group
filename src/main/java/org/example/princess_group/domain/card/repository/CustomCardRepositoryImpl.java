package org.example.princess_group.domain.card.repository;

import static org.example.princess_group.domain.card.entity.QCard.card;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public void changeOrder(Card card, Integer target) {
        em.merge(card);
        if (card.getOrder().equals(target)) {
            return;
        } else if (card.getOrder() < target) {
            pullCardOrderAtList(card.getListId(), card.getOrder() + 1, target);
        } else {
            pushCardOrderAtList(card.getListId(), target, card.getOrder() - 1);
        }
        card.setOrder(target);
        em.flush();
        em.clear();
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
