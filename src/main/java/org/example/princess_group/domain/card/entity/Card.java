package org.example.princess_group.domain.card.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseEntity {

    private Long columnId;
    private String name;
    private String description;
    private String color;
    private Integer order;
    private LocalDateTime deadline;

    @Builder
    public Card(Long columnId, String name, String description, String color, Integer order,
        LocalDateTime deadline) {
        this.columnId = columnId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.order = order;
        this.deadline = deadline;
    }
}
