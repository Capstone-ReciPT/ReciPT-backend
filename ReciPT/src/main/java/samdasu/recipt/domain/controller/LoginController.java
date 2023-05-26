package samdasu.recipt.domain.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.User.LoginForm;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.security.config.jwt.JwtProperties;
import samdasu.recipt.security.exception.TokenNotExistException;
import samdasu.recipt.utils.login.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginForm form) {
        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser != null) {
            // 로그인 성공
            return ResponseEntity.status(OK).body("Login successful");
        } else {
            // 로그인 실패
            return ResponseEntity.status(UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        DecodedJWT jwt = extractAndDecodeToken(authorizationHeader);

        if (jwt != null) {
            Date expirationDate = new Date(System.currentTimeMillis());

            String jwtToken = JWT.create()
                    .withSubject(jwt.getSubject())
                    .withExpiresAt(expirationDate)
                    .sign(Algorithm.HMAC512(JwtProperties.SECRET));
            log.info("jwtToken = {}", jwtToken);
        } else {
            throw new TokenNotExistException("로그아웃하는데 필요한 토큰을 찾지 못했습니다!");
        }

        return ResponseEntity.ok("Logout successful!!");
    }

    private DecodedJWT extractAndDecodeToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 접두사 제거

            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build();

                // token decode
                return verifier.verify(token);
            } catch (Exception e) {
                // 토큰 디코드 실패 또는 유효하지 않은 토큰
                // 처리 로직 추가 또는 예외 처리
                throw new TokenNotExistException("로그아웃하는데 필요한 토큰을 찾지 못했습니다!");
            }
        }
        return null; // 토큰이 없거나 올바른 형식이 아닌 경우 null 반환
    }

//    @GetMapping("/login/oauth2/code/google")
    
}
