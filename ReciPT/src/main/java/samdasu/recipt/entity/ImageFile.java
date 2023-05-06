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
    private String filename;
    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "register_id")
    private RegisterRecipe registerRecipe;

    //== 연관관계 편의 메서드 ==//

    public void setRegisterRecipe(RegisterRecipe registerRecipe) {
        this.registerRecipe = registerRecipe;
    }


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public ImageFile(String filename, String type, byte[] imageData) {
        this.filename = filename;
        this.type = type;
        this.imageData = imageData;
    }

    public static ImageFile createImageFile(String filename, String type, byte[] imageData) {
        return new ImageFile(filename, type, imageData);
    }

    //==비지니스 로직==//
    
}
