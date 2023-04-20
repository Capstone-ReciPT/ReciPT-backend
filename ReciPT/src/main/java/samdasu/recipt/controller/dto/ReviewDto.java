package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewDto {
    private String userName;
    private String title;
    private String comment;
    private Long viewCount;
    private GptRecipe gptRecipe;
    private DbRecipe dbRecipe;
    private List<ImageFileDto> imageFiles;

    public ReviewDto(Review review) {
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
