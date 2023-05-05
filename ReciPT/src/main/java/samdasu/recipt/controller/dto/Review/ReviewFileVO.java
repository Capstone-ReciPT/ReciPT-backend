package samdasu.recipt.controller.dto.Review;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReviewFileVO {
    private String userId;
    private String title;
    private String comment;
    private List<MultipartFile> files;

    private Double ratingScore;
}