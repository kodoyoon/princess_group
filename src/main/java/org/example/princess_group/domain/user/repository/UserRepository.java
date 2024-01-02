package org.example.princess_group.domain.user.repository;

import java.util.Optional;
import org.example.princess_group.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);

}
