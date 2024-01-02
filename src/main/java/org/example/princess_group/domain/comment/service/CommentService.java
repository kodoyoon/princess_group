package org.example.princess_group.domain.comment.service;

import java.util.List;
import org.example.princess_group.domain.comment.dto.request.CreateCommentRequest;
import org.example.princess_group.domain.comment.dto.request.UpdateCommentRequest;
import org.example.princess_group.domain.comment.entity.Comment;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;

import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;

public interface CommentService {

    boolean isValidId(Long commentId);


    List<Comment> getComments(Long id);

    void createComments(Long id, CreateCommentRequest request);

    void updateComments(Long id, UpdateCommentRequest request);

    void deleteComments(Long id);
}
