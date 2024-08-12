package KKSC.page.domain.board.service.impl;

import KKSC.page.domain.board.dto.request.AddCommentRequest;
import KKSC.page.domain.board.dto.request.UpdateCommentRequest;
import KKSC.page.domain.board.entity.Board;
import KKSC.page.domain.board.entity.Comment;
import KKSC.page.domain.board.repository.BoardRepository;
import KKSC.page.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl {

    private final CommentRepository commentRepository; // 댓글 레포지토리
    private final BoardRepository boardRepository; // 게시글 레포지토리

    /**
     * 댓글 추가
     *
     * @param boardId 게시글의 ID
     * @param request 추가할 댓글의 정보가 담긴 요청 객체
     * @return 추가된 댓글
     */
    public Comment save(Long boardId, AddCommentRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + boardId));

        Comment comment = new Comment(request.getContent(), board);
        return commentRepository.save(comment);
    }

    /**
     * 특정 댓글 조회
     *
     * @param id 댓글의 ID
     * @return 조회된 댓글
     */
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElse(null); // 댓글이 없으면 null 반환
    }

    /**
     * 특정 게시글에 달린 모든 댓글 조회
     *
     * @param boardId 게시글의 ID
     * @return 게시글에 달린 모든 댓글 목록
     */
    public List<Comment> findAllByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    /**
     * 댓글 수정
     *
     * @param id 댓글의 ID
     * @param request 수정할 내용이 담긴 요청 객체
     * @return 수정된 댓글
     */
    public Comment update(Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + id));

        comment.setContent(request.getContent()); // 댓글 내용 수정
        return commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param id 댓글의 ID
     */
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. ID: " + id);
        }
        commentRepository.deleteById(id);
    }
}
