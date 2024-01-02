package org.example.princess_group.domain.board.dto;


import org.example.princess_group.domain.board.entity.Board;

public record CreateBoardRequest(
    Long boardId,
    String title,
    String author,
    String backgroundcolor,
    String contents
) {

    public CreateBoardRequest(Board saveBoard) {
        this(
            saveBoard.getId(),
            saveBoard.getTitle(),
            saveBoard.getAuthor(),
            saveBoard.getBackgroundcolor(),
            saveBoard.getContents()
        );
    }
}



