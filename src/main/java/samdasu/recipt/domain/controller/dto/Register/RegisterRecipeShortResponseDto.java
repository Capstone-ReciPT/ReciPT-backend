package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class RegisterRecipeShortResponseDto {
    private String username;
    private String foodName;
    private Integer likeCount;
    private String category;
    private String thumbnailImage;
    private byte[] thumbnailImageByte;

    public RegisterRecipeShortResponseDto(RegisterRecipe registerRecipe) {
        username = registerRecipe.getUser().getUsername();
        foodName = registerRecipe.getFoodName();
        likeCount = registerRecipe.getLikeCount();
        category = registerRecipe.getCategory();
        thumbnailImage = registerRecipe.getThumbnailImage();
    }

    public static RegisterRecipeShortResponseDto createRecipeShortResponseDto(RegisterRecipe recipe) {
        return new RegisterRecipeShortResponseDto(recipe);
    }
}