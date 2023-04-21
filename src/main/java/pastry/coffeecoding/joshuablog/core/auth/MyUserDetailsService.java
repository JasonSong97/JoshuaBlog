package pastry.coffeecoding.joshuablog.core.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pastry.coffeecoding.joshuablog.model.user.User;
import pastry.coffeecoding.joshuablog.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Bad Credential") // failHandler가 처리함
        );
        return new MyUserDetails(userPS);
    }
}
