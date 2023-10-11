package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.common.BaseTimeEntity;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponseDto {
    private String foodName;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private String thumbnailImage;
    private List<String> image;
    private LocalDateTime lastModifiedDate;
    public RegisterResponseDto(RegisterRecipe registerRecipe) {
        foodName = registerRecipe.getFoodName();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        thumbnailImage = registerRecipe.getThumbnailImage();
        image = registerRecipe.getImage();
        lastModifiedDate = registerRecipe.getLastModifiedDate();
    }

    public static RegisterResponseDto createRegisterResponseDto(RegisterRecipe registerRecipe) {
        return new RegisterResponseDto(registerRecipe);
    }
}
