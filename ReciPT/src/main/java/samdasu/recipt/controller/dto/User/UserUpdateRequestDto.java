package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String password;
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
