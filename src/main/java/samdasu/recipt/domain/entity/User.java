package samdasu.recipt.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.domain.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity implements UserDetails {
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
    @Column
    @ElementCollection(fetch = LAZY)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RegisterRecipe> registerRecipes = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Gpt> gpt = new ArrayList<>();


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public User(String username, String loginId, String password, Integer age, String profile, List<String> roles) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.age = age;
        this.profile = profile;
        this.roles = roles;
    }


    public static User createUser(String username, String loginId, String password, Integer age, String profile, List<String> roles) {
        User user = new User(username, loginId, password, age, profile, roles);
        return user;
    }


    //==비지니스 로직==//
    public void updateUserInfo(String newPassword) {
        password = newPassword;
    }


    //==Security==//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        authorities = getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
