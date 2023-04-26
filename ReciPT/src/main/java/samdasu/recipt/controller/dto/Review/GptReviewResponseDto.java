package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFile.ImageFileResponseDto;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GptReviewResponseDto {
    @NotEmpty
    private Long reviewId;
    @NotNull
    private String username;
    @NotNull
    private String title;
    @NotNull
    private String comment;
    private Integer viewCount;
    private Integer likeCount;
    private GptRecipe gptRecipe;
    private List<ImageFileResponseDto> imageFiles;

    public GptReviewResponseDto(Review review) {
        reviewId = review.getReviewId();
        username = review.getUser().getUsername();
        title = review.getTitle();
        comment = review.getComment();
        viewCount = review.getViewCount();
        likeCount = review.getLikeCount();
        gptRecipe = review.getGptRecipe();
        imageFiles = review.getImageFiles().stream()
                .map(imageFile -> new ImageFileResponseDto(imageFile))
                .collect(Collectors.toList());
    }

    public GptReviewResponseDto(Long reviewId, String username, String title, String comment, Integer viewCount, Integer likeCount, GptRecipe gptRecipe, List<ImageFileResponseDto> imageFiles) {
        this.reviewId = reviewId;
        this.username = username;
        this.title = title;
        this.comment = comment;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.gptRecipe = gptRecipe;
        this.imageFiles = imageFiles;
    }

    public static GptReviewResponseDto createGptReviewResponseDto(Long reviewId, String username, String title, String comment, Integer viewCount, Integer likeCount, GptRecipe gptRecipe, List<ImageFileResponseDto> imageFiles) {
        return new GptReviewResponseDto(reviewId, username, title, comment, viewCount, likeCount, gptRecipe, imageFiles);
    }


}
