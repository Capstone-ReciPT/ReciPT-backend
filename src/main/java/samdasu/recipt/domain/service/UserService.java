package samdasu.recipt.domain.service;

import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.User.*;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.entity.enums.Authority;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.UserRepository;
import samdasu.recipt.security.SecurityUtil;
import samdasu.recipt.security.config.jwt.JwtTokenProvider;
import samdasu.recipt.utils.Image.AttachImage;
import samdasu.recipt.utils.Image.UploadService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final LoginResponse response;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;

    @Value("${image.user.path}")
    private String userProfile;


    @Counted("signUp")
    @Transactional
    public Long signUp(UserSignUpDto signUpDto, MultipartFile uploadFile) { //회원가입
        validateLogin(signUpDto);

        AttachImage upload = uploadService.uploadOne(userProfile, uploadFile, signUpDto.getUsername());
        log.info("upload.getSavedName() = {}", upload.getSavedName());

        User user = User.createUser(signUpDto.getUsername(), signUpDto.getLoginId(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getAge(), upload.getSavedName(), Collections.singletonList(Authority.ROLE_USER.name()));
        return userRepository.save(user).getUserId();
    }

    private void validateLogin(UserSignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new IllegalArgumentException("Fail: Already Exist UserName!");
        }
        if (userRepository.existsByLoginId(signUpDto.getLoginId())) {
            throw new IllegalArgumentException("Fail: Already Exist ID!");
        }
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Fail: Please Check Password!");
        }
    }


    public ResponseEntity<?> login(LoginDto login) {
        if (userRepository.findByLoginId(login.getLoginId()).orElse(null) == null) {
            return response.fail("해당하는 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        log.info("Login : tokenInfo.getAccessToken() = {}",tokenInfo.getAccessToken());
        log.info("Login : tokenInfo.getRefreshToken() = {}",tokenInfo.getRefreshToken());
        log.info("Login : tokenInfo.getRefreshTokenExpirationTime() = {}",tokenInfo.getRefreshTokenExpirationTime());

        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> reissue(UserReissueDto reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("fail","Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User loginId 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User loginId 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("fail", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("fail","Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        log.info("Reissue : tokenInfo.getAccessToken() = {}",tokenInfo.getAccessToken());
        log.info("Reissue : tokenInfo.getRefreshToken() = {}",tokenInfo.getRefreshToken());
        log.info("Reissue : tokenInfo.getRefreshTokenExpirationTime() = {}",tokenInfo.getRefreshTokenExpirationTime());


        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(UserLogoutDto logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User loginId 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User loginId 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        log.info("Logout : tokenInfo.getAccessToken() = {}",logout.getAccessToken());
        log.info("Logout : tokenInfo.getRefreshToken() = {}",logout.getRefreshToken());

        return response.success("로그아웃 되었습니다.");
    }

    public ResponseEntity<?> authority() {
        // SecurityContext에 담겨 있는 authentication userLoginId 정보
        String loginId = SecurityUtil.getCurrentUserId();
        log.info("SecurityUtil.getCurrentUserId() = {}", SecurityUtil.getCurrentUserId());

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        user.getRoles().add(Authority.ROLE_ADMIN.name());
        userRepository.save(user);

        return response.success();
    }

    @Transactional
    public Long update(Long userId, UserUpdateRequestDto updateRequestDto) {
        User user = findUserById(userId);
        String newPassword = passwordEncoder.encode(updateRequestDto.getPassword());

        user.updateUserInfo(newPassword);
        return userId;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
    }
}
