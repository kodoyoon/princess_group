package org.example.princess_group.domain.card.repository;

import java.util.Optional;
import org.example.princess_group.domain.card.entity.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CustomCardRepository {

    @EntityGraph(attributePaths = "workers")
    Optional<Card> findFetchById(Long id);

    Long countByListId(Long listId);

    boolean existsByIdAndListId(Long cardId, Long ListId);
}
