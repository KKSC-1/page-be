package KKSC.page.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 수정 요청 DTO
 */
@Getter
@Setter
public class UpdateCommentRequest {
    @NotBlank
    private String content; // 댓글 내용
}