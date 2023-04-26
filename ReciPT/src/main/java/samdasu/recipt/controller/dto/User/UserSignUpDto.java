package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.User;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSignUpDto {
    @NotNull
    private String username;
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
    private String userAllergy;

    public UserSignUpDto(String username, String loginId, String password, String passwordConfirm, String userAllergy) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.userAllergy = userAllergy;
    }

    public UserSignUpDto(User user, String passwordConfirm) {
        this.userAllergy = user.getUserAllergy();
        this.username = user.getUsername();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.passwordConfirm = passwordConfirm;
    }

}
