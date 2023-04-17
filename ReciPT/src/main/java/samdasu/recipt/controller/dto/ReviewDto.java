package samdasu.recipt.service.dto;

import samdasu.recipt.entity.Review;

public class ReviewDto {
    private String userName;
    private Long imageId;
    private String comment;

    private int review_grade;

    public ReviewDto(Review review) {
        userName = review.getUser().getUserName();
        imageId = review.getImageFile().getId();
        comment = review.getComment();
        review_grade = review.getReview_grade();
    }
}
