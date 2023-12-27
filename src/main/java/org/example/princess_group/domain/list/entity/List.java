package org.example.princess_group.domain.list.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
public class List extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int order;

}
