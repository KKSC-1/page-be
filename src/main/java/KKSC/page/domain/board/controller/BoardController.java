package KKSC.page.domain.board.controller;

import KKSC.page.domain.board.dto.request.AddBoardRequest;
import KKSC.page.domain.board.dto.request.UpdateBoardRequest;
import KKSC.page.domain.board.dto.response.BoardResponse;
import KKSC.page.domain.board.entity.Board;
import KKSC.page.domain.board.service.impl.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings({"unused","RedundantSuppression"})
@RequiredArgsConstructor
@RequestMapping("/api/boards") // REST API의 기본 경로를 설정합니다. 단수형으로 URI를 선택합니다.
@RestController
public class BoardController {
    private final BoardServiceImpl boardService; // BoardService 인스턴스를 주입받습니다.

    /**
     * 게시글 추가
     *
     * @param request 추가할 게시글의 정보가 담긴 요청 객체
     * @return 추가된 게시글의 정보를 담은 ResponseEntity
     */
    @PreAuthorize("hasRole('permission_level1')")
    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody AddBoardRequest request) {
        Board savedBoard=boardService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard); // BoardService를 통해 게시글을 추가하고 반환합니다.
    }

    /**
     * 모든 게시글 조회
     *
     * @return 모든 게시글 목록을 담은 ResponseEntity
     */
    @GetMapping
    public List<BoardResponse> findAllBoards() {
        List<BoardResponse> board = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();
        return board; // 전체 게시글 목록을 반환합니다.
    }

    /**
     * 특정 게시글 조회
     *
     * @param id 조회할 게시글의 ID
     * @return 조회된 게시글의 정보를 담은 ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findBoard(@PathVariable("id") long id) {
        Board board = boardService.findById(id); // ID를 기반으로 게시글을 조회합니다.
        if (board == null) {
            return ResponseEntity.notFound().build(); // 조회된 게시글이 없는 경우 404 Not Found를 반환합니다.
        }
        return ResponseEntity.ok(new BoardResponse(board)); // 조회된 게시글을 반환합니다.
    }

    /**
     * 게시글 삭제
     *
     * @param id 삭제할 게시글의 ID
     * @return 삭제 완료 후의 ResponseEntity
     */
    @PreAuthorize("hasRole('permission_level1')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") long id) {
        boardService.delete(id); // ID를 기반으로 게시글을 삭제합니다.
        return ResponseEntity.ok().build(); // 삭제 완료를 나타내는 ResponseEntity를 반환합니다.
    }

    /**
     * 게시글 수정
     *
     * @param id      수정할 게시글의 ID
     * @param request 수정할 내용이 담긴 요청 객체
     * @return 수정된 게시글의 정보를 담은 ResponseEntity
     */
    @PreAuthorize("hasRole('permission_level1')")
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") long id, @RequestBody UpdateBoardRequest request) {
        Board updatedBoard = boardService.update(id, request); // ID를 기반으로 게시글을 수정합니다.
        if (updatedBoard == null) {
            return ResponseEntity.notFound().build(); // 수정할 게시글을 찾지 못한 경우 404 Not Found를 반환합니다.
        }
        return ResponseEntity.ok(updatedBoard); // 수정된 게시글을 반환합니다.
    }
}
