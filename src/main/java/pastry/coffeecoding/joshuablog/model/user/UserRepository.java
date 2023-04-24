package pastry.coffeecoding.joshuablog.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = :username") // 네임쿼리작동 때문에 직접 작성
    Optional<User> findByUsername(@Param("username") String username);
}
