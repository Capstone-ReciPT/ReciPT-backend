package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDetailInfoDto {
    private Long registerRecipeId;
    private String foodName;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private String thumbnail;
    private Integer likeCount;
    private Long viewCount;
    private Double ratingScore;
    private Integer ratingPeople;
    private List<String> image;
    private byte[] thumbnailByte;
    private List<byte[]> imageByte = new ArrayList<>();
    private LocalDateTime lastModifiedDate;
    public RegisterDetailInfoDto(RegisterRecipe registerRecipe) {
        registerRecipeId = registerRecipe.getRegisterId();
        foodName = registerRecipe.getFoodName();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        likeCount = registerRecipe.getLikeCount();
        viewCount = registerRecipe.getViewCount();
        ratingScore = registerRecipe.getRatingScore();
        ratingPeople = registerRecipe.getRatingPeople();
        thumbnail = registerRecipe.getThumbnailImage();
        image = registerRecipe.getImage();
        lastModifiedDate = registerRecipe.getLastModifiedDate();
    }

    public static RegisterDetailInfoDto createRegisterDetailInfoDto(RegisterRecipe registerRecipe) {
        return new RegisterDetailInfoDto(registerRecipe);
    }
}
