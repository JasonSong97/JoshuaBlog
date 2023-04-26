package pastry.coffeecoding.joshuablog.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pastry.coffeecoding.joshuablog.DummyEntity;
import pastry.coffeecoding.joshuablog.model.board.Board;
import pastry.coffeecoding.joshuablog.model.board.BoardRepository;
import pastry.coffeecoding.joshuablog.model.user.User;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@ActiveProfiles("test") // test profile만 실행
@Import(BCryptPasswordEncoder.class)
@DataJpaTest
public class BoardRepositoryTest extends DummyEntity {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        join_good();
    }

    @Test
    public void findAll_test(){
        // Lazy 전략 -> Board만 조회
        boardRepository.findAll();
    }

    @Test
    public void findAllV2_test(){
        // Lazy 전략 -> Board만 조회
        List<Board> boardList = boardRepository.findAll();
        // Lazy Loading 하기 (20바퀴)
        boardList.stream().forEach(board -> {
            System.out.println(board.getUser().getUsername()); // select 2번 user cos
        });
    }

    @Test
    public void findAllV3_test() throws JsonProcessingException { // EAGER
        // Lazy 전략 -> Board만 조회
        PageRequest pageRequest = PageRequest.of(0, 0, Sort.by("id").descending());
        Page<Board> boardPG = boardRepository.findAll(pageRequest);
        String responseBody = new ObjectMapper().writeValueAsString(boardPG);
        System.out.println("디버그 : " + responseBody);
    }

    @Test
    public void findByUserId_test(){
        List<Board> boardList = boardRepository.findByUserId(1L);
        boardList.stream().forEach(board -> {
            // loop 1번에 select  (ssar)
            // loop 2~10번 1차 캐싱 (PC)
            // loop 11번에 select  (cos)
            // loop 11~20번 1차 캐싱 (PC)
            System.out.println(board.getUser().getUsername()); // select 2번
        });
    }

    @Test
    public void findByUserIds_test() {
        List<Long> userIds = boardRepository.findUserIdDistinct();
        List<Board> boardList = boardRepository.findByUserIds(userIds); // 결과값 받아서 in 쿼리작동
        System.out.println(boardList.get(0).getUser().getUsername());
    }

    private void in_query_good(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            userList.add(newUser("user"+i, passwordEncoder));
        }
        userRepository.saveAll(userList);

        List<Board> boardList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            boardList.add(newBoard("제목"+i, userList.get(0)));
        }
        for (int i = 5; i < 9; i++) {
            boardList.add(newBoard("제목"+i, userList.get(1)));
        }
        for (int i = 9; i < 11; i++) {
            boardList.add(newBoard("제목"+i, userList.get(2)));
        }
        boardRepository.saveAll(boardList);
        em.clear();
    }

    private void join_good(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            userList.add(newUser("user"+i, passwordEncoder));
        }
        userRepository.saveAll(userList);

        List<Board> boardList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            boardList.add(newBoard("제목"+i, userList.get(i-1)));
        }
        boardRepository.saveAll(boardList);
        em.clear(); // 영속성 컨텍스트 비우기
    }
}