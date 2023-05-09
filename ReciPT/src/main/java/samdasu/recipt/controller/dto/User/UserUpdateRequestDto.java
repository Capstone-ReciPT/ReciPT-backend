package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.entity.Profile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank
    private String password;
    private byte[] profileData;

    public UserUpdateRequestDto(String password, Profile profile) {
        this.password = password;
        profileData = profile.getProfileData();
    }

    public static UserUpdateRequestDto createUpdateUserInfo(String password, Profile profile) {
        return new UserUpdateRequestDto(password, profile);
    }

}
