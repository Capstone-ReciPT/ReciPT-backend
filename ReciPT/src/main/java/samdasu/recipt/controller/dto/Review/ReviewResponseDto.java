package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFileDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewResponseDto {
    private User user;
    private Long reviewId;
    private String userName;
    private String title;
    private String comment;
    private Integer viewCount;
    private GptRecipe gptRecipe;
    private DbRecipe dbRecipe;
    private List<ImageFileDto> imageFiles;

    public ReviewResponseDto(Review review) {
        reviewId = review.getReviewId();
        user = review.getUser();
        userName = review.getUser().getUserName();
        title = review.getTitle();
        comment = review.getComment();
        viewCount = review.getViewCount();
        gptRecipe = review.getGptRecipe();
        dbRecipe = review.getDbRecipe();
        imageFiles = review.getImageFiles().stream()
                .map(imageFile -> new ImageFileDto(imageFile))
                .collect(Collectors.toList());
    }

}
