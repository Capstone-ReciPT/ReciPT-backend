package samdasu.recipt.controller.dto.Allergy;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.User;

@Getter
@Setter
public class UserAllergyRequestDto {

    private String userAllergy;

    private User user;

    public UserAllergyRequestDto(String userAllergy, User user) {
        this.userAllergy = userAllergy;
        this.user = user;
    }

    public static UserAllergyRequestDto createUserAllergyRequestDto(String userAllergy, User user) {
        return new UserAllergyRequestDto(userAllergy, user);
    }

}
