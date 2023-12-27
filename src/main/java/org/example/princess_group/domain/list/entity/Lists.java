package org.example.princess_group.domain.list.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Lists extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
    @Column(nullable = false)
    private String name;
    @Column(name = "orders")
    private long order;

    public Lists(Board board, CreateListsRequest request, int order) {
        this.board = board;
        this.name = request.getName();
        this.order = order;
    }
}
