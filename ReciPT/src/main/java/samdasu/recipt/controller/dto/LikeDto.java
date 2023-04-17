package samdasu.recipt.service.dto;

import samdasu.recipt.entity.Like;

public class LikeDto {

    private String foodName;
    private Long reviewId;
    private int count;

    public LikeDto(Like like) {
        reviewId = like.getReview().getId();
        foodName = like.getFood().getFoodName();
        count = like.getCount();
    }
}
