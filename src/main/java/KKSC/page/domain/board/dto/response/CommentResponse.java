package KKSC.page.domain.board.dto.response;

import KKSC.page.domain.board.entity.Comment;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 응답 DTO
 */
@Getter
@Setter
public class CommentResponse {

    private Long id; // 댓글 ID
    private String content; // 댓글 내용
    private Long boardId; // 댓글이 달린 게시글 ID

    // 엔티티 객체를 DTO로 변환하는 생성자
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId(); // 댓글이 달린 게시글의 ID
    }
}