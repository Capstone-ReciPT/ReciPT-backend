package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotNull;

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

    public ReviewRequestDto(Review review) {
        title = review.getTitle();
        comment = review.getComment();
    }

    public ReviewRequestDto(String username, String title, String comment, Double ratingScore) {
        this.username = username;
        this.title = title;
        this.comment = comment;
        this.ratingScore = ratingScore;
    }

    public static ReviewRequestDto createReviewRequestDto(String username, String title, String comment, Double ratingScore) {
        return new ReviewRequestDto(username, title, comment, ratingScore);
    }
}
