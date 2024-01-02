package org.example.princess_group.domain.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.comment.dto.request.CreateCommentRequest;
import org.example.princess_group.domain.comment.entity.Comment;
import org.example.princess_group.domain.comment.service.CommentService;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getComments(@PathVariable(name = "cardId") Long id) {
        List<Comment> response = commentService.getComments(id);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("댓글 조회에 성공했습니다.")
                .data(response)
                .build()
        );
    }

    @PostMapping("/{cardId}")
    public ResponseEntity<?> createComments(@PathVariable(name = "cardId") Long id, @RequestBody
    CreateCommentRequest request) {
        commentService.createComments(id, request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("댓글 저장에 성공했습니다.")
                .build()
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComments(@PathVariable(name = "commentId") Long id, @RequestBody
    CreateCommentRequest request) {
        commentService.updateComments(id, request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 수정에 성공했습니다.")

                .build()
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable(name = "commentId") Long id) {
        commentService.deleteComments(id);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("리스트 삭제에 성공했습니다.")
                .build()
        );
    }
}
