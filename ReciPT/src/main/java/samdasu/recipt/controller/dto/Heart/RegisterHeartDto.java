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

    public RegisterHeartDto(Long userId, Long registerId) {
        this.userId = userId;
        this.registerId = registerId;
    }

    public static RegisterHeartDto createDbHeartDto(Long userId, Long registerId) {
        return new RegisterHeartDto(userId, registerId);
    }

    public RegisterHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        registerId = heart.getRegisterRecipe().getRegisterId();
    }


}


