package pastry.coffeecoding.joshuablog.model.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final EntityManager em;
    private final int SIZE = 8;

    public Page<Board> findAll(int page) {
        int startPosition = page * SIZE;
        // 모든전략 Lazy-loading + 필요시 join fetch 사용
        List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user order by b.id desc")
                .setFirstResult(startPosition) // 시작번호
                .setMaxResults(SIZE) // 개수
                .getResultList();
        Long totalCount = em.createQuery("select count(b) from Board b", Long.class).getSingleResult(); // findAll 내부적 발동 쿼리
        return new PageImpl<>(boardListPS, PageRequest.of(page, SIZE), totalCount);
    }

    public Page<Board> findAllByKeyword(int page, String keyword) {
        int startPosition = page * SIZE;
        List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user where b.title like :keyword order by b.id desc")
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(startPosition) // startPosition
                .setMaxResults(SIZE) // size
                .getResultList();
        Long totalCount = em.createQuery("select count(b) from Board b where b.title like :keyword", Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
        return new PageImpl<>(boardListPS, PageRequest.of(page, SIZE), totalCount);
    }
}
