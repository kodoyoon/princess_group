package org.example.princess_group.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.princess_group.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column
    String userId;
    @Column
    String password;


    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
