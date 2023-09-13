package samdasu.recipt.domain.controller.dto.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequestDto {
    
    private String password;
//    private byte[] profileData;

    public UserUpdateRequestDto(String newPassword) {
        password = newPassword;
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String newPassword) {
        return new UserUpdateRequestDto(newPassword);
    }

}
