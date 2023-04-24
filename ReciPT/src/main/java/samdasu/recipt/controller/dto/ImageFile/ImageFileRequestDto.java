package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import samdasu.recipt.entity.Review;

@Getter
@Setter
public class ImageFileRequestDto {
    @Value("${file.dir}")
    private String fileDir;
    private String originalFilename;
    private String storeFilename;
    private Review reviews;

    public ImageFileRequestDto(String fileDir, String originalFilename, String storeFilename, Review reviews) {
        this.fileDir = fileDir;
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
        this.reviews = reviews;
    }

    public static ImageFileRequestDto createImageFileRequestDto(String fileDir, String originalFilename, String storeFilename, Review reviews) {
        return new ImageFileRequestDto(fileDir, originalFilename, storeFilename, reviews);
    }
}
