package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ImageFileResponseDto {
    @NotEmpty
    private Long imageId;
    private String originalFilename;
    private String storeFilename;
    private Review review;

    public ImageFileResponseDto(ImageFile imageFile) {
        imageId = imageFile.getImageId();
        review = imageFile.getReview();
    }

    public ImageFileResponseDto(Long imageId, Review review) {
        this.imageId = imageId;
        this.review = review;
    }

    public static ImageFileResponseDto createImageFileResponseDto(Long imageId, Review review) {
        return new ImageFileResponseDto(imageId, review);
    }

}
