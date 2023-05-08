package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLikeResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private byte[] profile;
    private List<RecipeHeartDto> recipeHeartDtos;
    private List<RegisterHeartDto> registerHeartDtos;


    /**
     * 유저가 누른 좋아요
     */
    public UserLikeResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        password = user.getPassword();
        profile = user.getProfile();
        recipeHeartDtos = user.getHearts().stream()
                .map(heart -> new RecipeHeartDto(heart))
                .collect(Collectors.toList());
        registerHeartDtos = user.getHearts().stream()
                .map(heart -> new RegisterHeartDto(heart))
                .collect(Collectors.toList());
    }

    public static UserLikeResponseDto createUserResponseDto(User user) {
        return new UserLikeResponseDto(user);
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
