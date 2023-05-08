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
    private byte[] profile;

    public UserUpdateRequestDto(String password, byte[] profile) {
        this.password = password;
        this.profile = profile;
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String password, byte[] profile) {
        return new UserUpdateRequestDto(password, profile);
    }

}
