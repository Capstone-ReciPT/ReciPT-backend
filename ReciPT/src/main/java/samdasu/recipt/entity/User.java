package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String userAllergy;

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<RecentSearch> recentSearches = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!


    public User(String username, String loginId, String password, String userAllergy) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.userAllergy = userAllergy;
    }

    public static User createUser(String username, String loginId, String password, String userAllergy) {
        return new User(username, loginId, password, userAllergy);
    }

    //==비지니스 로직==//
    public void updateUserInfo(UserUpdateRequestDto requestDto) {
        password = requestDto.getPassword();
        userAllergy = requestDto.getUserAllergy();
    }

}
