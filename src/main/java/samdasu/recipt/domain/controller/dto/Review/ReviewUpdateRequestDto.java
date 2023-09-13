package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewUpdateRequestDto {
    @NotNull
    private String comment;

    public ReviewUpdateRequestDto(String comment) {
        this.comment = comment;
    }

    public static ReviewUpdateRequestDto createReviewUpdateRequestDto(String comment) {
        return new ReviewUpdateRequestDto(comment);
    }
}
