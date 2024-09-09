package KKSC.page.domain.board.controller;

import KKSC.page.domain.board.dto.request.AddCommentRequest;
import KKSC.page.domain.board.dto.request.UpdateCommentRequest;
import KKSC.page.domain.board.dto.response.CommentResponse;
import KKSC.page.domain.board.entity.Comment;
import KKSC.page.domain.board.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
@RestController
public class CommentController {

    private final CommentServiceImpl commentService;

    /**
     * 댓글 추가
     *
     * @param boardId 게시글의 ID
     * @param request 추가할 댓글의 정보가 담긴 요청 객체
     * @return 추가된 댓글의 정보를 담은 ResponseEntity
     */
    @PreAuthorize("hasRole('permission_level1')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@PathVariable("boardId") Long boardId,
                                      @Valid @RequestBody AddCommentRequest request) {
        Comment savedComment = commentService.create(boardId, request);
        return new CommentResponse(savedComment); // CommentResponse DTO로 변환하여 반환
    }

    /**
     * 특정 댓글 조회
     *
     * @param boardId 게시글의 ID
     * @param id      댓글의 ID
     * @return 조회된 댓글의 정보를 담은 ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findComment(@PathVariable("boardId") Long boardId,
                                                       @PathVariable("id") Long id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CommentResponse(comment));
    }

    /**
     * 모든 댓글 조회
     *
     * @param boardId 게시글의 ID
     * @return 모든 댓글 목록을 담은 ResponseEntity
     */
    @GetMapping
    public List<CommentResponse> findAllComments(@PathVariable("boardId") Long boardId) {
        return commentService.findAllByBoardId(boardId)
                .stream()
                .map(CommentResponse::new)
                .toList(); // CommentResponse DTO 목록 반환
    }

    /**
     * 댓글 수정
     *
     * @param boardId  게시글의 ID
     * @param id       수정할 댓글의 ID
     * @param request  수정할 내용이 담긴 요청 객체
     * @return 수정된 댓글의 정보를 담은 ResponseEntity
     */
    @PreAuthorize("hasRole('permission_level1')")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("boardId") Long boardId,
                                                         @PathVariable("id") Long id,
                                                         @Valid @RequestBody UpdateCommentRequest request) {
        Comment updatedComment = commentService.update(id, request);
        if (updatedComment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CommentResponse(updatedComment));
    }

    /**
     * 댓글 삭제
     *
     * @param boardId 게시글의 ID
     * @param id      삭제할 댓글의 ID
     * @return 삭제 완료 후의 ResponseEntity
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("boardId") Long boardId,
                              @PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
