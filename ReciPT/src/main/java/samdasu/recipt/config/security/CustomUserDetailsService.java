package samdasu.recipt.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.repository.UserRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //loginId이 DB에 있는지 확인해줘야함
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User principal = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Failed: 일치하는 로그인 정보가 없습니다."));

        UserResponseDto userResponseDto = new UserResponseDto(principal);
        return userResponseDto; //시큐리티의 세션에 유저정보가 저장이됨. (원래는 콘솔창에 뜨는 user, pw가 있었음)
    }
}