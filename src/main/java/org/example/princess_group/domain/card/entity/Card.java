package org.example.princess_group.domain.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CARD")
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
    public Card(Long listId, String name, String description, String color, Integer order,
        LocalDateTime deadline) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.order = order;
        this.deadline = deadline;
    }
}
