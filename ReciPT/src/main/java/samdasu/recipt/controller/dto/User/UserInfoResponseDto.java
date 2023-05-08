package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.entity.User;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfoResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private byte[] profile;
    private String loginId;
    private String password;
    private Integer age; //연령별: 레시피 탭에서 필요

    /**
     * 프로필 세팅 탭
     */
    public UserInfoResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        profile = user.getProfile();
        loginId = user.getLoginId();
        password = user.getPassword();
        age = user.getAge();
    }


    public static UserInfoResponseDto createUserResponseDto(User user) {
        return new UserInfoResponseDto(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
