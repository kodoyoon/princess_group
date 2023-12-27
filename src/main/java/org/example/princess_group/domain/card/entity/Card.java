package org.example.princess_group.domain.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.domain.card.dto.UpdateCardRequest;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseEntity {

    @Column(nullable = false)
    private Long listId;
    private String name;
    private String description;
    private String color;
    @Column(name = "orders")
    private Integer order;
    private LocalDateTime deadline;

    @Builder
    private Card(Long listId, String name, String description, String color, Integer order,
        LocalDateTime deadline) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.order = order;
        this.deadline = deadline;
    }

    public void update(UpdateCardRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.deadLine() != null) {
            this.deadline = request.deadLine();
        }
        if (request.color() != null) {
            this.color = request.color();
        }
    }
}
