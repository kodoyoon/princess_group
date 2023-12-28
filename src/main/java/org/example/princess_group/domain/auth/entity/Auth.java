package org.example.princess_group.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Auth extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Authority authority;    //Invite, Create, Read, Write, Update, Delete

    @Column(nullable = false)
    private String type;    //Board, List, Card

    @Column(nullable = false)
    private Long domainId;    //Board: 1


}
