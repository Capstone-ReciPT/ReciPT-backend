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

    private String filename; //사용자가 임의로 만든 파일 명
    private String fileOriginName; //원본 파일 명(filename은 겹칠 수도 있음)
    private String fileUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review reviews;


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public ImageFile(String filename, String fileOriginName, String fileUrl, Review reviews) {
        this.filename = filename;
        this.fileOriginName = fileOriginName;
        this.fileUrl = fileUrl;
//        addImageFile(reviews);
    }

    public void setReview(Review reviews) {
        this.reviews = reviews;
    }
}
