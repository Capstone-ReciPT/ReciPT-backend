package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotNull
    private String password;
    @NotNull
    private String userAllergy;

    public UserUpdateRequestDto(String password, String userAllergy) {
        this.password = password;
        this.userAllergy = userAllergy;
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String password, String userAllergy) {
        return new UserUpdateRequestDto(password, userAllergy);
    }

    public UserUpdateRequestDto(UserResponseDto userResponseDto) {
        this.password = userResponseDto.getPassword();
        this.userAllergy = userResponseDto.getUserAllergy();
    }

}
