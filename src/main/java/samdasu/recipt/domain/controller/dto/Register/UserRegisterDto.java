package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto {
    private String foodName;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private Integer likeCount;
    private Double ratingResult;
    private Integer ratingPeople;
    private String thumbnailImage;
    private List<String> images;
    private LocalDateTime lastModifiedDate;
    public UserRegisterDto(RegisterRecipe registerRecipe) {
        foodName = registerRecipe.getFoodName();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        likeCount = registerRecipe.getLikeCount();
        ratingResult = registerRecipe.getRatingScore();
        ratingPeople = registerRecipe.getRatingPeople();
        thumbnailImage = registerRecipe.getThumbnailImage();
        images = registerRecipe.getImage();
        lastModifiedDate = registerRecipe.getLastModifiedDate();
    }

    public static UserRegisterDto createUserRegisterDto(RegisterRecipe registerRecipe) {
        return new UserRegisterDto(registerRecipe);
    }
}
