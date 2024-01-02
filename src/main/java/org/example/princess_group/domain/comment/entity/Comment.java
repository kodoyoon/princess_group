package org.example.princess_group.domain.comment.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
public class Comment extends BaseEntity {

    private Long cardId;
    private String details;

    @Builder
    public Comment(String details, Long cardId) {
        this.details = details;
        this.cardId = cardId;
    }

    public void update(String details) {
        this.details =details;
    }
}
