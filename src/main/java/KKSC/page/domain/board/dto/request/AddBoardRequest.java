package KKSC.page.domain.board.dto.request;

import KKSC.page.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddBoardRequest {
    private String title;
    private String content;
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}

