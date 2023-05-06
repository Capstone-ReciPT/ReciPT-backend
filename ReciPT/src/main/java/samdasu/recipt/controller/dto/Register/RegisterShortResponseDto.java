package samdasu.recipt.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.RegisterRecipe;

import javax.validation.constraints.NotNull;

/**
 * 홈화면 같이 짧게 보여줄 때 사용...?
 */
@Getter
@Setter
public class RegisterShortResponseDto {
    @NotNull
    private String fodName;
    private Integer likeCount; //레시피 좋아요
    private Double ratingResult;
    private Integer ratingPeople;

    public RegisterShortResponseDto(RegisterRecipe recipe) {
        fodName = recipe.getFoodName();
        likeCount = recipe.getLikeCount();
        ratingResult = recipe.getRatingScore();
        ratingPeople = recipe.getRatingPeople();
    }


    public static RegisterShortResponseDto creatShortInfo(RegisterRecipe recipe) {
        return new RegisterShortResponseDto(recipe);
    }

}
