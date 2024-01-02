package org.example.princess_group.domain.board.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.princess_group.domain.board.dto.CreateBoardRequest;
import org.example.princess_group.domain.board.dto.UpdateBoardRequest;
import org.example.princess_group.global.dto.RootResponse;

public interface BoardService {

    CreateBoardRequest createBoard(String title, String author, String backgroundcolor,
        String contents);

    List<CreateBoardRequest> getBoards();

    CreateBoardRequest getBoard(Long boardId);

    @Transactional
    void updateBoard(Long boardId, UpdateBoardRequest updateBoardRequest);

    boolean isValidId(Long boardId);

    @Transactional
    void deleteBoard(Long boardId);

    RootResponse inviteUser(String userId);
}
