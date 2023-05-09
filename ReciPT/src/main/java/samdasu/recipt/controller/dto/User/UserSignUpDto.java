package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.entity.User;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpDto {
    @NotBlank
    private String username;

    @NotBlank
    private Integer age;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;

    public UserSignUpDto(String username, Integer age, String loginId, String password, String passwordConfirm) {
        this.username = username;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public UserSignUpDto(User user, String passwordConfirm) {
        this.username = user.getUsername();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.passwordConfirm = passwordConfirm;
    }
}
