package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.controller.dto.Allergy.UserAllergyResponseDto;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDto implements UserDetails {
    @NotBlank
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String loginId;
    @NotNull
    private String password;

    private List<UserAllergyResponseDto> userAllergyResponseDto;
    private List<DbHeartDto> dbHeartDto;
    private List<GptHeartDto> gptHeartDto;
    //    private List<ReviewRequestDto> reviews;
    private List<String> reviewTitle;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();

        dbHeartDto = user.getHearts().stream()
                .map(heart -> new DbHeartDto(heart))
                .collect(Collectors.toList());
        gptHeartDto = user.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
        reviewTitle = user.getReviews().stream()
                .map(review -> review.getTitle())
                .collect(Collectors.toList());
    }

    public UserResponseDto(User user, Long userId) {
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();
        userAllergyResponseDto = user.getUserAllergies().stream()
                .map(userAllergy -> new UserAllergyResponseDto(userAllergy))
                .collect(Collectors.toList());
        List<String> reviewTitle = user.getReviews().stream()
                .map(review -> review.getTitle())
                .collect(Collectors.toList());
    }

    public static UserResponseDto createUserResponseDto(User user, Long userId) {
        return new UserResponseDto(user, userId);
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
