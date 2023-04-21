package pastry.coffeecoding.joshuablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pastry.coffeecoding.joshuablog.dto.user.UserRequest;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // SecurityConfig bean 등록

    // insert, update, delete -> try-catch
    @Transactional
    public void 회원가입(UserRequest.JoinInDto joinInDto) {
        try {

            // 1. 패스워드 암호화f
            joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));

            // 2. DB 저장
            userRepository.save(joinInDto.toEntity()); // 내부에서 터진것은 잡지 못하기 때문에
        } catch (Exception e) {
            throw new RuntimeException("회원가입 오류 : " + e.getMessage());
        }

    } // 더티채킹, DB 세션 종료(OSIV=false)
}
