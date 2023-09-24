package samdasu.recipt.utils.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AttachImage {
    private Long id;
    private String fileName;
    private String uploadPath;
    private String savedName;

    public AttachImage(Long id, String fileName, String uploadPath, String savedName) {
        this.id = id;
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.savedName = savedName;
    }
}
