package org.example.princess_group.domain.board.repository;

import org.example.princess_group.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
