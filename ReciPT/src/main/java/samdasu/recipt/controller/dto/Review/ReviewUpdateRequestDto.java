package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewUpdateRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String comment;

    public ReviewUpdateRequestDto(Review review) {
        title = review.getTitle();
        comment = review.getComment();
    }

    public ReviewUpdateRequestDto(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public static ReviewUpdateRequestDto createDbReviewRequestDto(String title, String comment) {
        return new ReviewUpdateRequestDto(title, comment);
    }
}
