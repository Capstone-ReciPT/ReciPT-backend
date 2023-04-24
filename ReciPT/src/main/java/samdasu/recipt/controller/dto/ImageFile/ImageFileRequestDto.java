package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageFileRequestDto {
    private String originalFilename;
    private String storeFilename;

    public ImageFileRequestDto(String originalFilename, String storeFilename) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
    }

    public static ImageFileRequestDto createImageFileRequestDto(String originalFilename, String storeFilename) {
        return new ImageFileRequestDto(originalFilename, storeFilename);
    }
}
