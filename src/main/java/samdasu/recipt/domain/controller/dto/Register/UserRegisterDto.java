package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto {
    private Long registerRecipeId;
    private String foodName;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private Integer likeCount;
    private Double ratingResult;
    private Integer ratingPeople;
    private String thumbnailImage;
    private byte[] thumbnailImageByte;
    private LocalDateTime lastModifiedDate;

    public UserRegisterDto(RegisterRecipe registerRecipe) {
        registerRecipeId = registerRecipe.getRegisterId();
        foodName = registerRecipe.getFoodName();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        likeCount = registerRecipe.getLikeCount();
        ratingResult = registerRecipe.getRatingScore();
        ratingPeople = registerRecipe.getRatingPeople();
        thumbnailImage = registerRecipe.getThumbnailImage();
        lastModifiedDate = registerRecipe.getLastModifiedDate();
    }

    public static UserRegisterDto createUserRegisterDto(RegisterRecipe registerRecipe) {
        return new UserRegisterDto(registerRecipe);
    }
}
