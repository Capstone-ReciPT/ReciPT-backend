package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.User;

@Getter
@Setter
public class UserSignUpDto {
    private String userName;
    private String loginId;
    private String password;
    private String passwordConfirm;
    private String userAllergy;

    public UserSignUpDto(String userName, String loginId, String password, String passwordConfirm, String userAllergy) {
        this.userName = userName;
        this.loginId = loginId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.userAllergy = userAllergy;
    }

    public UserSignUpDto(User user, String passwordConfirm) {
        this.userAllergy = user.getUserAllergy();
        this.userName = user.getUserName();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.passwordConfirm = passwordConfirm;
    }

}
