package samdasu.recipt.domain.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private byte[] profileData;
    private String loginId;
    private String password;
    private Integer age; //연령별: 레시피 탭에서 필요
    private List<RecipeHeartDto> recipeHeartDtos;
    private List<RegisterHeartDto> registerHeartDtos;
    private List<RecipeReviewResponseDto> recipeReviewResponseDtos;
    private List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos;
    private List<RegisterResponseDto> registerResponseDtos;

    /**
     * 프로필 세팅 탭
     */
    public UserResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        profileData = user.getProfile().getProfileData();
        loginId = user.getLoginId();
        password = user.getPassword();
        age = user.getAge();
        recipeHeartDtos = user.getHearts().stream()
                .filter(heart -> heart != null && heart.getRecipe() != null && heart.getRecipe().getRecipeId() != null) // null 값 필터링
                .map(heart -> new RecipeHeartDto(heart))
                .collect(Collectors.toList());
        registerHeartDtos = user.getHearts().stream()
                .filter(heart -> heart != null && heart.getRegisterRecipe() != null && heart.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
                .map(heart -> new RegisterHeartDto(heart))
                .collect(Collectors.toList());
        recipeReviewResponseDtos = user.getReviews().stream()
                .map(review -> new RecipeReviewResponseDto(review))
                .collect(Collectors.toList());
        registerRecipeReviewResponseDtos = user.getReviews().stream()
                .map(review -> new RegisterRecipeReviewResponseDto(review))
                .collect(Collectors.toList());
        registerResponseDtos = user.getRegisterRecipes().stream()
                .map(registerRecipe -> new RegisterResponseDto(registerRecipe))
                .collect(Collectors.toList());
        
    }

    public static UserResponseDto createUserResponseDto(User user) {
        return new UserResponseDto(user);
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
