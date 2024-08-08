package KKSC.page.domain.board.dto.response;

import KKSC.page.domain.board.entity.Board;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 응답 DTO
 */
@Getter
public class BoardResponse {

    private Long id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private List<KKSC.page.domain.board.dto.response.CommentResponse> comments; // 게시글의 댓글 목록

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.comments = board.getComments()
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}