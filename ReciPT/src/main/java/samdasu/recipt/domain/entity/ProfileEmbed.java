package samdasu.recipt.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEmbed {

    private String filename;
    private String type;

    @Lob
    @Column(name = "profile", length = 1000)
    private byte[] profileData;


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public ProfileEmbed(String filename, String type, byte[] profileData) {
        this.filename = filename;
        this.type = type;
        this.profileData = profileData;
    }

    //==비지니스 로직==//

}
