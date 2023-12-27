package org.example.princess_group.domain.card.repository;

import java.util.Optional;
import org.example.princess_group.domain.card.entity.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

    @EntityGraph(attributePaths = "workers")
    Optional<Card> findFetchById(Long id);

    Long countByListId(Long listId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Card c set c.order = c.order + 1 where c.listId = :listId and c.order >= :number")
    void postponeOrderByListIdAndGreaterThanNumber(@Param("listId") Long listId,
        @Param("number") Integer number);
}
