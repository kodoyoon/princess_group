package org.example.princess_group.domain.card.repository;

import org.example.princess_group.domain.card.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    void deleteByCardId(Long cardId);
}