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

    public UserUpdateRequestDto(String password) {
        this.password = password;
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String password) {
        return new UserUpdateRequestDto(password);
    }

    public UserUpdateRequestDto(UserResponseDto userResponseDto) {
        this.password = userResponseDto.getPassword();
    }

}
