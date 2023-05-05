package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageFileRequestDto {
    private Long itemId;
    private String itemName;
    private List<MultipartFile> imageFiles; //이미지 다중 업로드
    private MultipartFile attachFile;

    public ImageFileRequestDto(String itemName, List<MultipartFile> imageFiles, MultipartFile attachFile) {
        this.itemName = itemName;
        this.imageFiles = imageFiles;
        this.attachFile = attachFile;
    }

    public static ImageFileRequestDto createImageFileRequestDto(String itemName, List<MultipartFile> imageFiles, MultipartFile attachFile) {
        return new ImageFileRequestDto(itemName, imageFiles, attachFile);
    }
}
