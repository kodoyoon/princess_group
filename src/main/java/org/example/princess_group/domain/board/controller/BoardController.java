package org.example.princess_group.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.dto.BoardRequest;
import org.example.princess_group.domain.board.dto.CreateBoardRequest;
import org.example.princess_group.domain.board.dto.UpdateBoardRequest;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.board.service.BoardServiceImpl;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")  // 보드생성
    public ResponseEntity<?> createBoard(
        @RequestBody BoardRequest request
    ) {
        CreateBoardRequest respone = boardService.createBoard(
            request.getTitle(),
            request.getAuthor(),
            request.getBackgroundcolor(),
            request.getContents());
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드생성에 성공했습니다.")
                .data(respone)
                .build()
        );
    }

    @GetMapping("/api/boards") // 보드 전체조회
    public ResponseEntity<?> getBoards() {
        List<CreateBoardRequest> responseList = boardService.getBoards();
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드 전체조회에 성공했습니다.")
                .data(responseList)
                .build()
        );
    }

    @GetMapping("/api/boards/{boardId}") //보드 단건조회
    public ResponseEntity<?> getBoard(
        @PathVariable(name = "boardId") Long boardId
    ) {
        CreateBoardRequest responseList = boardService.getBoard(boardId);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드 단건조회에 성공했습니다.")
                .data(responseList)
                .build()
        );
    }

    @PatchMapping("/api/boards/{boardId}") // 보드수정
    public ResponseEntity<?>  updateBoard(
        @PathVariable(name = "boardId") Long boardId,
        @RequestBody UpdateBoardRequest request
    ) {
        boardService.updateBoard(boardId, request);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드 수정에 성공했습니다.")
                .build()
        );
    }

    @DeleteMapping("/api/boards/{boardId}") // 보드삭제
    public ResponseEntity<?> deleteBoard(
        @PathVariable(name = "boardId") Long boardId
    ) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드 삭제에 성공했습니다.")
                .build()
        );
    }

    @PostMapping("/api/boards/invitation")
    public ResponseEntity<?> inviteUser(
        @RequestBody String userId
    ) {
        return ResponseEntity.ok(
            RootResponse.builder()
                .status("200")
                .msg("보드 초대에 성공했습니다.")
                .build()
        );
    }
}
