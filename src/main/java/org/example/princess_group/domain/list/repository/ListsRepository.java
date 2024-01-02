package org.example.princess_group.domain.list.repository;

import java.util.List;
import org.example.princess_group.domain.list.entity.Lists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListsRepository extends JpaRepository<Lists, Long>, ListsOrderRepository {

    List<Lists> findAllByBoardId(Long boardId);

    long countByBoardId(Long boardId);
}
