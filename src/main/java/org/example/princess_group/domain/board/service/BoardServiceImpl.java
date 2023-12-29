package org.example.princess_group.domain.board.service;

import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.dto.CreateBoardRequest;
import org.example.princess_group.domain.board.dto.UpdateBoardRequest;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl {
 private final BoardRepository boardRepository;
 public CreateBoardRequest createBoard(String title, String author, String backgroundcolor,String contents) {
   Board board = new Board(title, author, backgroundcolor, contents);
   Board saveBoard = boardRepository.save(board);
   String saveTitle = saveBoard.getTitle();
   String saveAuthor = saveBoard.getAuthor();
   String saveBackgroundcolor = saveBoard.getBackgroundcolor();
   String saveContents = saveBoard.getContents();
   return new CreateBoardRequest(saveTitle,saveAuthor, saveBackgroundcolor,saveContents);
 }

  public List<CreateBoardRequest> getBoards() {
  return boardRepository.findAll().stream().map(
      board -> new CreateBoardRequest(board.getTitle(),board.getAuthor(), board.getBackgroundcolor(), board.getContents())
            ).toList();
  }

  public CreateBoardRequest getBoard(Long boardId) {
    Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
    return new CreateBoardRequest(board);
  }

  @Transactional
  public void updateBoard(Long boardId, UpdateBoardRequest updateBoardRequest) {
   Board findBoard = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
   findBoard.updateTitle(updateBoardRequest.getTitle());
   findBoard.updateAuthor(updateBoardRequest.getAuthor());
   findBoard.updateBackgroundcolor(updateBoardRequest.getBackgroundcolor());
   findBoard.updateContents(updateBoardRequest.getContents());
 }
  @Override
  public boolean isValidId(Long boardId) {
    return false;
  }

        @Transactional
    public void deleteBoard(Long boardId) {
   Board findBoard = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
   boardRepository.delete(findBoard);
    }

    public RootResponse inviteUser(String userId) {
   return new RootResponse(String.valueOf(HttpStatus.OK.value()), userId, "유저 등록 성공");

    }

}
