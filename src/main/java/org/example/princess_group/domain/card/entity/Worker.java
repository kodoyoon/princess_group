package org.example.princess_group.domain.card.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker extends BaseEntity {

    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
}
