package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.ImageFile;

import java.util.List;

@Getter
@Setter
public class ImageFileResponseDto {
    private Long imageId;
    private String itemName;
    private ImageFile attachFile; //고객이 업로드한 파일명
    private List<ImageFile> imageFiles;

    public ImageFileResponseDto(ImageFile imageFile) {

    }

    public ImageFileResponseDto(String itemName, ImageFile attachFile, List<ImageFile> imageFiles) {
        this.itemName = itemName;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }

    public static ImageFileResponseDto createImageFileResponseDto(String itemName, ImageFile attachFile, List<ImageFile> imageFiles) {
        return new ImageFileResponseDto(itemName, attachFile, imageFiles);
    }

}
