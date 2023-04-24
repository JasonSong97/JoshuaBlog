package pastry.coffeecoding.joshuablog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pastry.coffeecoding.joshuablog.model.board.Board;
import pastry.coffeecoding.joshuablog.model.board.BoardRepository;
import pastry.coffeecoding.joshuablog.model.user.User;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JoshuaBlogApplication extends DummyEntity{ // 더미 추가 -> 테스트를 위해

    @Profile("dev") // dev만 작동
    @Bean
    CommandLineRunner init(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, BoardRepository boardRepository) {
        return args -> {

            User ssar = newUser("ssar", passwordEncoder);
            User cos = newUser("cos", passwordEncoder);
            List<Board> boardList = new ArrayList<>();

            for (int i = 1; i < 11; i++) {
                boardList.add(newBoard("제목"+i, ssar));
            }
            for (int i = 11; i < 21; i++) {
                boardList.add(newBoard("제목"+i, cos));
            }
            userRepository.saveAll(Arrays.asList(ssar, cos));
            boardRepository.saveAll(boardList);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(JoshuaBlogApplication.class, args);
    }

}
