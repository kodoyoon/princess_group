package org.example.princess_group.domain.board.service;

import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Override
    public boolean isValidId(Long boardId) {
        return false;
    }
}