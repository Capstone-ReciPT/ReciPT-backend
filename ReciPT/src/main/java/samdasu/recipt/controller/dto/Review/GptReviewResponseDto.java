package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFile.ImageFileDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DbReviewResponseDto {
    @NotNull
    private String userName;
    @NotNull
    private String title;
    @NotNull
    private String comment;
    private Integer viewCount;
    private Integer likeCount;
    private DbRecipe dbRecipe;
    private List<ImageFileDto> imageFiles;

    public DbReviewResponseDto(Review review) {
        userName = review.getUser().getUserName();
        title = review.getTitle();
        comment = review.getComment();
        viewCount = review.getViewCount();
        likeCount = review.getLikeCount();
        dbRecipe = review.getDbRecipe();
        imageFiles = review.getImageFiles().stream()
                .map(imageFile -> new ImageFileDto(imageFile))
                .collect(Collectors.toList());
    }

}
