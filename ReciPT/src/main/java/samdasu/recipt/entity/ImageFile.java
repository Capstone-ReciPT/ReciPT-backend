package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String filename;
    private String fileOriginName;
    private String fileUrl;

    @OneToOne(mappedBy = "imageFile", fetch = LAZY) //Cascade
    private Review reviews;

    public void addImageFile(Review review) {
        this.reviews = review;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public ImageFile(String filename, String fileOriginName, String fileUrl) {
        this.filename = filename;
        this.fileOriginName = fileOriginName;
        this.fileUrl = fileUrl;
    }
}
