package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDto implements UserDetails {
    private Long userId;
    private String username;
    private String loginId;
    private String password;

    private Integer age;

    //연령별: 레시피 탭에서 필요

    private List<Heart> hearts;

    private List<Review> reviews;

    private List<RegisterRecipe> registerRecipes;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();
        age = user.getAge();


//        dbHeartDto = user.getHearts().stream()
//                .map(heart -> new RecipeHeartDto(heart))
//                .collect(Collectors.toList());
//        gptHeartDto = user.getHearts().stream()
//                .map(heart -> new GptHeartDto(heart))
//                .collect(Collectors.toList());
//        reviewTitle = user.getReviews().stream()
//                .map(review -> review.getTitle())
//                .collect(Collectors.toList());
    }

    public UserResponseDto(User user, Long userId) {
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();
//        userAllergyResponseDto = user.getUserAllergies().stream()
//                .map(userAllergy -> new UserAllergyResponseDto(userAllergy))
//                .collect(Collectors.toList());
//        reviewTitle = user.getReviews().stream()
//                .map(review -> review.getTitle())
//                .collect(Collectors.toList());
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
