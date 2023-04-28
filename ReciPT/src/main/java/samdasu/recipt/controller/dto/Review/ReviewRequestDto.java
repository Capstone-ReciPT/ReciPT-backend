package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.ImageFile.ImageFileResponseDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String title;
    @NotNull
    private String comment;
    private Double ratingScore;
    private GptRecipe gptRecipe;
    private DbRecipe dbRecipe;
    private List<ImageFileResponseDto> imageFiles;

    public ReviewRequestDto(Review review) {
        title = review.getTitle();
        comment = review.getComment();
        gptRecipe = review.getGptRecipe();
        dbRecipe = review.getDbRecipe();
        imageFiles = review.getImageFiles().stream()
                .map(ImageFileResponseDto::new)
                .collect(Collectors.toList());
    }

    public ReviewRequestDto(String username, String title, String comment, Double ratingScore, DbRecipe dbRecipe, List<ImageFileResponseDto> imageFiles) {
        this.username = username;
        this.title = title;
        this.comment = comment;
        this.ratingScore = ratingScore;
        this.dbRecipe = dbRecipe;
        this.imageFiles = imageFiles;
    }

    public ReviewRequestDto(String username, String title, String comment, Double ratingScore, GptRecipe gptRecipe, List<ImageFileResponseDto> imageFiles) {
        this.username = username;
        this.title = title;
        this.comment = comment;
        this.ratingScore = ratingScore;
        this.gptRecipe = gptRecipe;
        this.imageFiles = imageFiles;
    }

    public static ReviewRequestDto createDbReviewRequestDto(String username, String title, String comment, Double ratingScore, DbRecipe dbRecipe, List<ImageFileResponseDto> imageFiles) {
        return new ReviewRequestDto(username, title, comment, ratingScore, dbRecipe, imageFiles);
    }

    public static ReviewRequestDto createGptReviewRequestDto(String username, String title, String comment, Double ratingScore, GptRecipe gptRecipe, List<ImageFileResponseDto> imageFiles) {
        return new ReviewRequestDto(username, title, comment, ratingScore, gptRecipe, imageFiles);
    }

}
