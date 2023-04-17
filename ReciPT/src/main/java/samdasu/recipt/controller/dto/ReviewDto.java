package samdasu.recipt.controller.dto;

import samdasu.recipt.entity.Review;

public class ReviewDto {
    private String userName;
    private Long imageId;
    private String comment;

    private int review_grade;

    public ReviewDto(Review review) {
        userName = review.getUser().getUserName();
        imageId = review.getImageFile().getImageId();
        comment = review.getComment();
        review_grade = review.getReview_grade();
    }
}
