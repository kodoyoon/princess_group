package org.example.princess_group.domain.board.service;


import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.princess_group.domain.board.dto.CreateBoardRequest;
import org.example.princess_group.domain.board.dto.UpdateBoardRequest;
import org.example.princess_group.domain.board.entity.Board;
import org.example.princess_group.domain.board.repository.BoardRepository;
import org.example.princess_group.global.dto.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public CreateBoardRequest createBoard(String title, String author, String backgroundcolor,
        String contents) {
        Board board = new Board(title, author, backgroundcolor, contents);
        Board saveBoard = boardRepository.save(board);
        Long save = saveBoard.getId();
        String saveTitle = saveBoard.getTitle();
        String saveAuthor = saveBoard.getAuthor();
        String saveBackgroundcolor = saveBoard.getBackgroundcolor();
        String saveContents = saveBoard.getContents();
        return new CreateBoardRequest(save,saveTitle, saveAuthor, saveBackgroundcolor, saveContents);
    }

    @Override
    public List<CreateBoardRequest> getBoards() {
        return boardRepository.findAll().stream().map(
            board -> new CreateBoardRequest(board.getId(), board.getTitle(), board.getAuthor(),
                board.getBackgroundcolor(), board.getContents())
        ).toList();
    }

    @Override
    public CreateBoardRequest getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        return new CreateBoardRequest(board);
    }
    @Override
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
        Board findBoard = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        return true;
    }
    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        boardRepository.delete(findBoard);
    }
    @Override
    public RootResponse inviteUser(String userId) {
        return new RootResponse(String.valueOf(HttpStatus.OK.value()), userId, "유저 등록 성공");

    }


}
