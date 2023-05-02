package samdasu.recipt.controller.dto.Allergy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAllergyUpdateRequestDto {

    private String userAllergy;

    public UserAllergyUpdateRequestDto(String userAllergy) {
        this.userAllergy = userAllergy;
    }

    public static UserAllergyUpdateRequestDto createUserAllergyRequestDto(String userAllergy) {
        return new UserAllergyUpdateRequestDto(userAllergy);
    }

}
