package pastry.coffeecoding.joshuablog.dto.user;

import lombok.Getter;
import lombok.Setter;
import pastry.coffeecoding.joshuablog.model.user.User;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinInDto {
        private String username;
        private String password;
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role("USER") // enum 사용 가능
                    .status(true)
                    .profile("person.png")
                    .build();
        }
    }
}
