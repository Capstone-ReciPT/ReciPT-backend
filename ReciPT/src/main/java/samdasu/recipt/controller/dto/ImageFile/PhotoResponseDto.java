package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import samdasu.recipt.entity.ImageFile;

@Getter
public class PhotoResponseDto {
    private Long fileId;  // 파일 id

    public PhotoResponseDto(ImageFile entity) {
        this.fileId = entity.getImageId();
    }
}