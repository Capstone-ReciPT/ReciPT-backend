package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponseDto {
    private Long registerId;
    private String foodName;
    private String title;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private Integer likeCount;
    private Double ratingResult;
    private Integer ratingPeople;
    private String thumbnailImage;
    private List<String> image;
    private List<RegisterRecipeReviewResponseDto> reviewResponseDtos;
    private List<RegisterHeartDto> heartDtos;

    public RegisterResponseDto(RegisterRecipe registerRecipe) {
        registerId = registerRecipe.getRegisterId();
        foodName = registerRecipe.getFoodName();
        title = registerRecipe.getTitle();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        likeCount = registerRecipe.getLikeCount();
        ratingResult = registerRecipe.getRatingScore();
        ratingPeople = registerRecipe.getRatingPeople();
        thumbnailImage = registerRecipe.getThumbnailImage();
        image = registerRecipe.getImage();
    }

    public static RegisterResponseDto createRegisterResponseDto(RegisterRecipe registerRecipe) {
        return new RegisterResponseDto(registerRecipe);
    }
}
