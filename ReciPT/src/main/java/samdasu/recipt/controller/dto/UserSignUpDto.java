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

    public UserSignUpDto(User user) {
        this.userAllergy = user.getUserAllergy();
        this.userName = user.getUserName();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
    }
}
