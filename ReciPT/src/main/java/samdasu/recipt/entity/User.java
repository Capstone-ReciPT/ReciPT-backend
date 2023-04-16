package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String loginId;
    private String password;
    private String userAllergy;

    @OneToMany(mappedBy = "user")
    private List<AllergyInfo> allergyInfos;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public User(String username, String loginId, String password, String userAllergy) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.userAllergy = userAllergy;
    }
}
