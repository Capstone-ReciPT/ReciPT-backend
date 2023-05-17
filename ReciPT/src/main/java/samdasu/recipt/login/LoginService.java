package samdasu.recipt.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * @return null이면 로그인 실패
     */
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }
}
