package org.example.princess_group.domain.comment.service;


import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.card.entity.Card;
import org.example.princess_group.domain.card.service.CardServiceImpl;
import org.example.princess_group.domain.comment.dto.CommentRequestDto;
import org.example.princess_group.domain.comment.dto.CommentResponseDto;
import org.example.princess_group.domain.comment.entity.Comment;
import org.example.princess_group.domain.comment.error.CommentErrorCode;
import org.example.princess_group.domain.comment.repository.CommentRepository;
import org.example.princess_group.domain.user.entity.User;
import org.example.princess_group.global.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardServiceImpl cardService;

    public CommentResponseDto addComment(User user, CommentRequestDto requestDto) {
        Card card = cardService.getCard(requestDto.getCardId());
        Comment comment = new Comment(user, card, requestDto);
        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, User user, CommentRequestDto requestDto) {
        Comment comment = getComment(id, user);
        comment.update(requestDto);

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    public void deleteComment(Long id, User user) {
        Comment comment = getComment(id, user);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ServiceException(CommentErrorCode.NOT_VALID_USER));


        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ServiceException(CommentErrorCode.NOT_VALID_USER);
        }
        return comment;
    }
}
