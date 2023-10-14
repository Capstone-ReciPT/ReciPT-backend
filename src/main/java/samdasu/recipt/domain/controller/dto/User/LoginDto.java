package samdasu.recipt.domain.controller.dto.User;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }

    public LoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public static LoginDto createLoginDto(String loginId, String password) {
        return new LoginDto(loginId, password);
    }
}
