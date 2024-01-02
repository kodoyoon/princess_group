package org.example.princess_group.domain.comment.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
