package samdasu.recipt.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.UserRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //스프링이 로그인 요청을 가로챌때 loginId, password변수 2개를 가로채는데
    //password 부분 처리는 알아서 처리,
    //loginId이 DB에 있는지 확인해줘야함
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User principal = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Failed: 일치하는 로그인 정보가 없습니다."));

        UserResponseDto userResponseDto = new UserResponseDto(principal);
        return userResponseDto; //시큐리티의 세션에 유저정보가 저장이됨. (원래는 콘솔창에 뜨는 user, pw가 있었음)
    }
}

