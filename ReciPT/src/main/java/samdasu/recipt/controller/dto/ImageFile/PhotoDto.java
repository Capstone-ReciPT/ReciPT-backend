package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoDto {
    private String origFileName;
    private String filePath;
    private Long fileSize;

    public PhotoDto(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public static PhotoDto createPhotoDto(String origFileName, String filePath, Long fileSize) {
        return new PhotoDto(origFileName, filePath, fileSize);
    }
}