package samdasu.recipt.security.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import samdasu.recipt.domain.repository.UserRepository;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("CustomUserDetailsService : 진입");
        UserDetails userDetails = userRepository.findByLoginId(loginId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Failed: 해당하는 회원을 찾을 수 없습니다."));

        log.info("userDetails.getUsername() = {}", userDetails.getUsername());
        log.info("userDetails.getAuthorities() = {}", userDetails.getAuthorities());
        return userDetails;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(samdasu.recipt.domain.entity.User user) {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthorities().toString());

        return new User(user.getUsername(), user.getPassword(), Collections.singleton(grantedAuthority));
    }
}
