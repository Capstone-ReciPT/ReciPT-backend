package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterHeartDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long registerId;

    private String foodName;

    public RegisterHeartDto(Long userId, Long registerId, String foodName) {
        this.userId = userId;
        this.registerId = registerId;
        this.foodName = foodName;
    }

    public static RegisterHeartDto createRegisterHeartDto(Long userId, Long registerId, String foodName) {
        return new RegisterHeartDto(userId, registerId, foodName);
    }

    public RegisterHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        registerId = heart.getRegisterRecipe().getRegisterId();
        foodName = heart.getRegisterRecipe().getFoodName();
    }


}


