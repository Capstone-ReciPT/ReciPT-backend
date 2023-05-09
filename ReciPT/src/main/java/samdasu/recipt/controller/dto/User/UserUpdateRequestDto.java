package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank
    private String password;

    public UserUpdateRequestDto(String password) {
        this.password = password;
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String password) {
        return new UserUpdateRequestDto(password);
    }

}
