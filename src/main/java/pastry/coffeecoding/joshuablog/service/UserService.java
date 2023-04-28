package pastry.coffeecoding.joshuablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pastry.coffeecoding.joshuablog.core.exception.csr.ExceptionApi400;
import pastry.coffeecoding.joshuablog.core.exception.ssr.Exception400;
import pastry.coffeecoding.joshuablog.core.exception.ssr.Exception500;
import pastry.coffeecoding.joshuablog.core.util.MyFileUtil;
import pastry.coffeecoding.joshuablog.dto.user.UserRequest;
import pastry.coffeecoding.joshuablog.model.user.User;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // SecurityConfig bean 등록

    @Value("${file.path}")
    private String uploadFolder;

    // insert, update, delete -> try-catch
    @Transactional
    public void 회원가입(UserRequest.JoinInDto joinInDto) {
        // 1. 유저네임 중복 확인
        Optional<User> userOP = userRepository.findByUsername(joinInDto.getUsername());
        if(userOP.isPresent()){
            throw new Exception400("username", "유저네임이 중복되었어요");
        }

        try {
            // 2. 패스워드 암호화
            joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));

            // 3. DB 저장 (고립성)
            userRepository.save(joinInDto.toEntity()); // 내부에서 터진것은 잡지 못하기 때문에
        } catch (Exception e) {
            throw new Exception500("회원가입 실패 : " + e.getMessage()); // CustomException 예정
        }

    } // 더티채킹, DB 세션 종료(OSIV=false)

    public User 회원프로필보기(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(()->
                        new Exception400("id", "해당 유저가 존재하지 않습니다"));
        return userPS;
    }

    @Transactional
    public User 프로필사진수정(MultipartFile profile, Long id) {

        try {
            String uuidImageName = MyFileUtil.write(uploadFolder, profile);

            User userPS = userRepository.findById(id)
                    .orElseThrow(()->new Exception500("로그인 된 유저가 DB에 존재하지 않음"));
            userPS.changeProfile(uuidImageName);
            return userPS;
        }catch (Exception e){
            throw new Exception500("프로필 사진 등록 실패 : "+e.getMessage());
        }
    } // 더티체킹 (업데이트)

    public void 유저네임중복체크(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        if(userOP.isPresent()){
            throw new ExceptionApi400("username", "유저네임이 중복되었어요");
        }
    }
}
