package org.example.princess_group.domain.comment.service;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public boolean isValidId(Long commentId) {
        return false;
    }
}
