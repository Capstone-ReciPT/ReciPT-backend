package samdasu.recipt.controller.dto;

import samdasu.recipt.entity.Like;

public class LikeDto {

    private String foodName;
    private Long reviewId;
    private int count;

    public LikeDto(Like like) {
        this.reviewId = like.getReview().getReviewId();
        this.foodName = like.getFood().getFoodName();
        this.count = like.getCount();
    }
}
