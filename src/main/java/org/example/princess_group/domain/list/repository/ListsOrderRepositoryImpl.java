package org.example.princess_group.domain.list.repository;

import com.querydsl.core.types.QList;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.list.entity.Lists;
import org.example.princess_group.domain.list.entity.QLists;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class ListsOrderRepositoryImpl implements ListsOrderRepository {

    EntityManager entityManager;
    JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
    @Override
    public List<Lists> orderChange(Long number) {
        return jpaQueryFactory.
            select(QLists.lists).
            from(QLists.lists).
            where(
                QLists.lists.order.gt(number)
            ).fetch();
    }

    @Override
    public long orderFind(Long id) {
        return jpaQueryFactory.
            select(QLists.lists.order).
            from(QLists.lists).
            where(QLists.lists.id.eq(id)).fetch().get(0);
    }

}
