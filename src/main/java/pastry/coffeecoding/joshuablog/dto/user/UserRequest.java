package pastry.coffeecoding.joshuablog.dto.user;

import lombok.Getter;
import lombok.Setter;
import pastry.coffeecoding.joshuablog.model.user.User;

import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinInDto {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role("USER") // enum 사용 가능
                    .status(true)
                    .profile("person.png") // 기본 프로필 사진 -> 나중에 바뀔 예정
                    .build();
        }
    }
}
