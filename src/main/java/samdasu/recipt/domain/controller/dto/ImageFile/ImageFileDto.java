package samdasu.recipt.domain.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.ImageFile;

@Getter
@Setter
public class ImageFileDto {
    private String filename;
    private String type;
    private byte[] imageData;

    public ImageFileDto(String filename, String type, byte[] imageData) {
        this.filename = filename;
        this.type = type;
        this.imageData = imageData;
    }

    public static ImageFileDto createImageFileRequestDto(String filename, String type, byte[] imageData) {
        return new ImageFileDto(filename, type, imageData);
    }

    public ImageFileDto(ImageFile imageFile) {
        filename = imageFile.getFilename();
        type = imageFile.getType();
        imageData = imageFile.getImageData();
    }
}
