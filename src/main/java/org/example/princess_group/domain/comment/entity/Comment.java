package org.example.princess_group.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.comment.dto.CommentRequestDto;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.global.entity.BaseEntity;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseEntity {
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(User user, Card card, CommentRequestDto requestDto) {
        this.user = user;
        this.card = card;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
