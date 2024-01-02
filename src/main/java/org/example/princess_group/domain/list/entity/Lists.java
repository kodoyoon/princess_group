package org.example.princess_group.domain.list.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.request.OrderChangeListsRequest;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Lists extends BaseEntity {

    @Column(nullable = false)
    private Long boardId;
    @Column(nullable = false)
    private String name;
    @Column(name = "orders")
    private Long order;

    @Builder
    public Lists(Long boardId, String name, Long order) {
        this.boardId = boardId;
        this.name = name;
        this.order = order;
    }

    public void update(CreateListsRequest request) {
        this.name = request.name();
    }

    public void updateOrderDelete() {
        this.order = order - 1;
    }

    public void updateOrderChange() {
        this.order = order++;
    }

    public void updateOrder(OrderChangeListsRequest request) {
        this.order = request.number();
    }
}
