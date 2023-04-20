package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String userName;
    private String loginId;
    private String password;
    private String userAllergy;

    public UserUpdateRequestDto(String userName, String loginId, String password, String userAllergy) {
        this.userName = userName;
        this.loginId = loginId;
        this.password = password;
        this.userAllergy = userAllergy;
    }

    public UserUpdateRequestDto(UserResponseDto userResponseDto) {
        this.userName = userResponseDto.getUserName();
        this.loginId = userResponseDto.getLoginId();
        this.password = userResponseDto.getPassword();
        this.userAllergy = userResponseDto.getUserAllergy();
    }

}
