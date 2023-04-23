package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ImageFileDto {
    @NotEmpty
    private Long imageId;
    @Value("${file.dir}")
    private String fileDir;
    private Review reviews;

    public ImageFileDto(ImageFile imageFile) {
        imageId = imageFile.getImageId();
        fileDir = imageFile.getFileDir();
        reviews = imageFile.getReviews();
    }
}
