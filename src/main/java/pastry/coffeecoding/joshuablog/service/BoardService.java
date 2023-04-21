package pastry.coffeecoding.joshuablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pastry.coffeecoding.joshuablog.dto.board.BoardRequest;
import pastry.coffeecoding.joshuablog.model.board.Board;
import pastry.coffeecoding.joshuablog.model.board.BoardQueryRepository;
import pastry.coffeecoding.joshuablog.model.board.BoardRepository;
import pastry.coffeecoding.joshuablog.model.user.User;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveInDTO saveInDTO, Long userId) {
        try {
            // 1. user 존재 확인
            User userPS = userRepository.findById(userId).orElseThrow(
                    () -> new RuntimeException("유저를 찾을 수 없습니다.")
            );

            // 2. 게시글 쓰기
            boardRepository.save(saveInDTO.toEntity(userPS));
        } catch (Exception e) {
            throw new RuntimeException("글쓰기 실패 : " + e.getMessage());
        }

    }

    @Transactional(readOnly = true) // 변경감지 하지마, 고립성 떄문에(repeatable read - 내가 트랜젝션걸어놓은 거를 변경해도 그냥 무시)
    public Page<Board> 글목록보기(Pageable pageAble) { // CSR은 DTO로 변경해서 돌려줘야 함
        // 1. 모든 전략은 Lazy: 이유는 필요할 때만 가져오려고
        // 2. 필요할 때는 직접 fetch join을 사용하라!
        return boardQueryRepository.findAll(pageAble);
    }
}
