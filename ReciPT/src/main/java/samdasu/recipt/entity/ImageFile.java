package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long imageId;
    @Value("${file.dir}")
    private String fileDir;
    private String originalFilename; //원본 파일 명(유저가 업로드한 이미지의 원본 파일명)
    private String storeFilename; //서버에 저장된 파일명

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review reviews;

    public void setReview(Review reviews) {
        this.reviews = reviews;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public ImageFile(String fileDir, String originalFilename, String storeFilename) {
        this.fileDir = fileDir;
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
    }

    public static ImageFile createImageFile(String fileDir, String originalFilename, String storeFilename) {
        return new ImageFile(fileDir, originalFilename, storeFilename);
    }


    //==비지니스 로직==//


}
