package org.example.princess_group.domain.comment.service;

import static org.example.princess_group.domain.comment.error.CommentErrorCode.NOT_EXIST_COMMENT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.comment.dto.request.CreateCommentRequest;
import org.example.princess_group.domain.comment.dto.request.UpdateCommentRequest;
import org.example.princess_group.domain.comment.entity.Comment;
import org.example.princess_group.domain.comment.repository.CommentRepository;
import org.example.princess_group.domain.list.dto.request.CreateListsRequest;
import org.example.princess_group.domain.list.dto.response.ReadListsResponse;
import org.example.princess_group.domain.list.dto.response.UpdateListsResponse;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    @Override
    public boolean isValidId(Long commentId) {
        return false;
    }

    @Override
    public List<Comment> getComments(Long id) {
        return repository.findByCardId(id);
    }

    @Override
    public void createComments(Long id, CreateCommentRequest request) {
        Comment comment = Comment.builder().details(request.details()).cardId(id).build();
        repository.save(comment);
    }

    @Override
    @Transactional
    public void updateComments(Long id, UpdateCommentRequest request) {
        Comment comment = repository.findById(id).orElseThrow(() -> new ServiceException(NOT_EXIST_COMMENT));
        comment.update(request.details());
    }

    @Override
    public void deleteComments(Long id) {
        Comment comment = repository.findById(id).orElseThrow(() -> new ServiceException(NOT_EXIST_COMMENT));
        repository.delete(comment);
    }
}
