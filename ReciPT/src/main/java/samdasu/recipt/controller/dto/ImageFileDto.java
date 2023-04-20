package samdasu.recipt.controller.dto;

import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.Review;

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
