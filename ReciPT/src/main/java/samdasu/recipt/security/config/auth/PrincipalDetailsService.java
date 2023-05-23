package samdasu.recipt.security.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService : 진입");
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Failed: 일치하는 로그인 정보가 없습니다."));

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(user);
    }
}
