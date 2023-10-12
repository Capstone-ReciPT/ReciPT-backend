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
    private String foodName;
    private String comment;
    private String category;
    private String ingredient;
    private String context;
    private String thumbnail;
    private List<String> image;
    private byte[] thumbnailByte;
    private List<byte[]> imageByte = new ArrayList<>();
    private LocalDateTime lastModifiedDate;
    public RegisterDetailInfoDto(RegisterRecipe registerRecipe) {
        foodName = registerRecipe.getFoodName();
        comment = registerRecipe.getComment();
        category = registerRecipe.getCategory();
        ingredient = registerRecipe.getIngredient();
        context = registerRecipe.getContext();
        thumbnail = registerRecipe.getThumbnailImage();
        image = registerRecipe.getImage();
        lastModifiedDate = registerRecipe.getLastModifiedDate();
    }

    public static RegisterDetailInfoDto createRegisterDetailInfoDto(RegisterRecipe registerRecipe) {
        return new RegisterDetailInfoDto(registerRecipe);
    }
}
