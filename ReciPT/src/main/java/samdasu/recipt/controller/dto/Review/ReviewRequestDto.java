package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFile.ImageFileDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewRequestDto {
    @NotBlank
    private User user;
    @NotNull
    private Long reviewId;
    @NotNull
    private String userName;
    @NotNull
    private String title;
    @NotNull
    private String comment;
    @NotNull
    private Integer viewCount;
    @NotNull
    private Integer likeCount;
    private GptRecipe gptRecipe;
    private DbRecipe dbRecipe;
    private List<ImageFileDto> imageFiles;

    public ReviewRequestDto(Review review) {
        reviewId = review.getReviewId();
        user = review.getUser();
        userName = review.getUser().getUserName();
        title = review.getTitle();
        comment = review.getComment();
        viewCount = review.getViewCount();
        likeCount = review.getLikeCount();
        gptRecipe = review.getGptRecipe();
        dbRecipe = review.getDbRecipe();
        imageFiles = review.getImageFiles().stream()
                .map(imageFile -> new ImageFileDto(imageFile))
                .collect(Collectors.toList());
    }

}
