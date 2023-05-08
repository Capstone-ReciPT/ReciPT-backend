package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRegisterResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private byte[] profile;
    private List<RegisterResponseDto> registerResponseDtos;

    /**
     * 유저가 등록한 레시피
     */
    public UserRegisterResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        password = user.getPassword();
        profile = user.getProfile();
        registerResponseDtos = user.getRegisterRecipes().stream()
                .map(registerRecipe -> new RegisterResponseDto(registerRecipe))
                .collect(Collectors.toList());
    }


    public static UserRegisterResponseDto createUserResponseDto(User user) {
        return new UserRegisterResponseDto(user);
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
