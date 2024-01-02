package org.example.princess_group.domain.list.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.entity.QLists;

@RequiredArgsConstructor
public class ListsOrderRepositoryImpl implements ListsOrderRepository {

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;
    @PostConstruct
    private void init() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Lists> orderChangeDelete(Long boardId, Long number) {
        return jpaQueryFactory.
            select(QLists.lists).
            from(QLists.lists).
            where(
                QLists.lists.boardId.eq(boardId),
                QLists.lists.order.gt(number)
            ).fetch();
    }

    @Override
    public List<Lists> orderChangeUpdate(Long boardId, Long number) {
        return jpaQueryFactory.
            select(QLists.lists).
            from(QLists.lists).
            where(
                QLists.lists.boardId.eq(boardId),
                QLists.lists.order.goe(number)
            ).fetch();
    }
}
