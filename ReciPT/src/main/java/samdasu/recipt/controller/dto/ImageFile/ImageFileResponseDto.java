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
        originalFilename = imageFile.getOriginalFilename();
        storeFilename = imageFile.getStoreFilename();
        review = imageFile.getReview();
    }

    public ImageFileResponseDto(Long imageId, String originalFilename, String storeFilename, Review review) {
        this.imageId = imageId;
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.review = review;
    }

    public static ImageFileResponseDto createImageFileResponseDto(Long imageId, String originalFilename, String storeFilename, Review review) {
        return new ImageFileResponseDto(imageId, originalFilename, storeFilename, review);
    }

}
