package org.example.princess_group.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.princess_group.domain.board.dto.BoardRequest;
import org.example.princess_group.domain.board.dto.CreateBoardRequest;
import org.example.princess_group.domain.board.dto.UpdateBoardRequest;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.service.BoardServiceImpl;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class BoardController {
  private final BoardServiceImpl boardService;
  @PostMapping("/api/boards")  // 보드생성
  public CreateBoardRequest createBoard(
      @RequestBody BoardRequest request
      ){
    CreateBoardRequest respone = boardService.createBoard(
        request.getTitle(),
        request.getAuthor(),
        request.getBackgroundcolor(),
        request.getContents());
         return respone;
  }
  @GetMapping("/api/boards") // 보드 전체조회
  public List<CreateBoardRequest> getBoards(){
    List<CreateBoardRequest> responseList = boardService.getBoards();
    return responseList;
  }

  @GetMapping("/api/boards/{boardId}") //보드 단건조회
  public ResponseEntity<CreateBoardRequest> getBoard(
      @PathVariable(name = "boardId") Long boardId
  ){
    CreateBoardRequest responseList = boardService.getBoard(boardId);
    return ResponseEntity.ok(responseList);
  }

  @PatchMapping("/api/boards/{boardId}") // 보드수정
  public ResponseEntity<Void> updateBoard(
      @PathVariable(name = "boardId") Long boardId,
      @RequestBody UpdateBoardRequest request
  ){
    boardService.updateBoard(boardId, request);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

      @DeleteMapping("/api/boards/{boardId}") // 보드삭제
      public ResponseEntity<Void> deleteBoard(
      @PathVariable(name = "boardId") Long boardId
      ){
       boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }

      @PostMapping("/api/boards/invitation")
      public RootResponse inviteUser(
      @RequestBody String userId
      ){
    return boardService.inviteUser(userId);
      }
}
