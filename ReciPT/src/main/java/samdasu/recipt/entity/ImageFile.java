package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String originalFilename; //원본 파일 명(유저가 업로드한 이미지의 원본 파일명)
    private String storeFilename; //서버에 저장된 파일명
    private String fileUrl;

    private Long fileSize;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    //== 연관관계 편의 메서드 ==//


    public void setReview(Review review) {
        this.review = review;

        if (!review.getImageFiles().contains(this)) {
            review.getImageFiles().add(this);
        }
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!

    /**
     * 이미지 업로드 1번에 1개
     */
    public ImageFile(String storeFilename, String originalFilename, String fileUrl) {
        this.storeFilename = storeFilename;
        this.originalFilename = originalFilename;
        this.fileUrl = fileUrl;
    }

    public static ImageFile createImageFile(String storeFilename, String originalFilename, String fileUrl) {
        return new ImageFile(storeFilename, originalFilename, fileUrl);
    }

    /**
     * 다중 이미지 업로드
     */

    public ImageFile(String originalFilename, String storeFilename) {
        this.originalFilename = originalFilename;
        this.storeFilename = storeFilename;
    }

    public static ImageFile UploadFile(String originalFilename, String storeFilename) {
        return new ImageFile(originalFilename, storeFilename);
    }


    //==비지니스 로직==//
    public ImageFile(String originalFilename, String fileUrl, Long fileSize) {
        this.originalFilename = originalFilename;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

    public static ImageFile createFileHandler(String originalFilename, String fileUrl, Long fileSize) {
        return new ImageFile(originalFilename, fileUrl, fileSize);
    }

}
