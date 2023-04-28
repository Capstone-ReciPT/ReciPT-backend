package samdasu.recipt.controller.dto.allergy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAllergyRequestDto {

    private String userAllergy;

    public UserAllergyRequestDto(String userAllergy){
        this.userAllergy=userAllergy;
    }
    public static UserAllergyRequestDto createUserAllergyRequestDto(String userAllergy) {
        return new UserAllergyRequestDto(userAllergy);
    }

}
