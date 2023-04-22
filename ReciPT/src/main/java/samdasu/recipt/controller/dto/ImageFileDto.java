package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.Review;

@Getter
@Setter
public class ImageFileDto {
    private Long imageId;
    private String filename;
    private String fileOriginName;
    private String fileUrl;
    private Review reviews;

    public ImageFileDto(ImageFile imageFile) {
        imageId = imageFile.getImageId();
        filename = imageFile.getFilename();
        fileOriginName = imageFile.getFileOriginName();
        fileUrl = imageFile.getFileUrl();
        reviews = imageFile.getReviews();
    }
}
