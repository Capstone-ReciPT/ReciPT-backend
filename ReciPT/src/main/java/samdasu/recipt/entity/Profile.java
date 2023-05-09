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
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long profileId;
    private String filename;
    private String type;

    @Lob
    @Column(name = "profile", length = 1000)
    private byte[] profileData;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //== 연관관계 편의 메서드 ==//

    public void setUser(User user) {
        this.user = user;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public Profile(String filename, String type, byte[] profileData) {
        this.filename = filename;
        this.type = type;
        this.profileData = profileData;
    }

    public static Profile createProfile(String filename, String type, byte[] profileData) {
        return new Profile(filename, type, profileData);
    }

    //==비지니스 로직==//

}
