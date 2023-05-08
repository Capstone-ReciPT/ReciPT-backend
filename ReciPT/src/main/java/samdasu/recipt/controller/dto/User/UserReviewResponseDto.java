package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserReviewResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private byte[] profile;
    private String password;
    private List<RecipeReviewResponseDto> recipeReviewResponseDtos;
    private List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos;


    /**
     * 작성한 리뷰 보기
     */
    public UserReviewResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        profile = user.getProfile();
        password = user.getPassword();
        recipeReviewResponseDtos = user.getReviews().stream()
                .map(review -> new RecipeReviewResponseDto(review))
                .collect(Collectors.toList());
        registerRecipeReviewResponseDtos = user.getReviews().stream()
                .map(review -> new RegisterRecipeReviewResponseDto(review))
                .collect(Collectors.toList());
    }


    public static UserReviewResponseDto createUserResponseDto(User user) {
        return new UserReviewResponseDto(user);
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
