package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageFileDto {
    @NotEmpty
    private Long imageId;
    private Review reviews;

    public ImageFileDto(ImageFile imageFile) {
        imageId = imageFile.getImageId();
        reviews = imageFile.getReviews();
    }

    public static List<ImageFileDto> createImageFileDto(ImageFile imageFile) {
        return new ArrayList<>(imageFile);
    }
}
