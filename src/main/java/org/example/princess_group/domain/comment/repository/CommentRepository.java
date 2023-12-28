package org.example.princess_group.domain.comment.repository;

import org.example.princess_group.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
