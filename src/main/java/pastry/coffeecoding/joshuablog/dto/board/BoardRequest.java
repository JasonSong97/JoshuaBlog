package pastry.coffeecoding.joshuablog.dto.board;

import lombok.Getter;
import lombok.Setter;
import pastry.coffeecoding.joshuablog.model.board.Board;
import pastry.coffeecoding.joshuablog.model.user.User;

public class BoardRequest {

    @Getter @Setter
    public static class SaveInDTO {
        private String title;
        private String content;

        public Board toEntity(User user, String thumbnail) {
            return Board.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .thumbnail(thumbnail)
                    .build();
        }
    }
}
