package samdasu.recipt.controller.dto.Allergy;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.UserAllergy;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserAllergyResponseDto {
    @NotEmpty
    private Long allergyId;
    private String userAllergy;

    public UserAllergyResponseDto(UserAllergy userAllergy) {
        this.allergyId = userAllergy.getAllergyId();
        this.userAllergy = userAllergy.getUserAllergy();
    }


    public static UserAllergyResponseDto createUserAllergyResponseDto(UserAllergy userAllergy) {
        return new UserAllergyResponseDto(userAllergy);
    }

}
