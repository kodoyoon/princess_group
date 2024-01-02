package org.example.princess_group.domain.comment.service;

import java.util.List;
import org.example.princess_group.domain.comment.dto.request.CreateCommentRequest;
import org.example.princess_group.domain.comment.entity.Comment;

public interface CommentService {

    boolean isValidId(Long commentId);


    List<Comment> getComments(Long id);

    void createComments(Long id, CreateCommentRequest request);

    void updateComments(Long id, CreateCommentRequest request);

    void deleteComments(Long id);
}
