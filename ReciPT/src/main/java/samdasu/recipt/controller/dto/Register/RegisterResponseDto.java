package samdasu.recipt.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFile.ImageFileDto;
import samdasu.recipt.entity.RegisterRecipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RegisterResponseDto {
    private Long registerId;
    private byte[] thumbnailImage;
    private String foodName;
    private String title;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private Integer likeCount;
    private Double ratingResult;
    private Integer ratingPeople;
    private List<ImageFileDto> imageFiles;

    public RegisterResponseDto(RegisterRecipe registerRecipe) {
        registerId = registerRecipe.getRegisterId();
        thumbnailImage = registerRecipe.getRegisterRecipeThumbnail().getThumbnailData();
        foodName = registerRecipe.getFoodName();
        title = registerRecipe.getTitle();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        likeCount = registerRecipe.getLikeCount();
        ratingResult = registerRecipe.getRatingScore();
        ratingPeople = registerRecipe.getRatingPeople();
        imageFiles = registerRecipe.getImageFiles().stream()
                .map(imageFile -> new ImageFileDto(imageFile))
                .collect(Collectors.toList());
    }

    public static RegisterResponseDto createRegisterResponseDto(RegisterRecipe registerRecipe) {
        return new RegisterResponseDto(registerRecipe);
    }
}
