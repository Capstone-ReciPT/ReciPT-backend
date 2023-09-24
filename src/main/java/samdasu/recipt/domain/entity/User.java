package samdasu.recipt.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.domain.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 255)
    private String loginId;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(nullable = false)
    private Integer age;

    private String profile;
    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RegisterRecipe> registerRecipes = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Gpt> gpt = new ArrayList<>();


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public User(String username, String loginId, String password, Integer age, String profile) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.age = age;
        this.profile = profile;
    }

    public static User createUser(String username, String loginId, String password, Integer age, String profile) {
        User user = new User(username, loginId, password, age, profile);
        return user;
    }


    //==비지니스 로직==//
    public void updateUserInfo(String newPassword) {
        password = newPassword;
    }

}
