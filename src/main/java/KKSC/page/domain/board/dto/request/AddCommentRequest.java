package KKSC.page.domain.board.dto.request;

import KKSC.page.domain.board.entity.Board;
import KKSC.page.domain.board.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequest {
    private String content;
    private Long boardId;

    public Comment toEntity(Board board) {
        return new Comment(content, board);
    }
}
