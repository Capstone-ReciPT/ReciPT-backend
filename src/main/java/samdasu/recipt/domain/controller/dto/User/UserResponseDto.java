package samdasu.recipt.domain.controller.dto.User;

import lombok.*;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Register.UserRegisterDto;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String username;
    private String loginId;
    private String password;
    private Integer age; //연령별: 레시피 탭에서 필요
    private String profile;
    private List<RecipeHeartDto> recipeHeartDtos;
    private List<RegisterHeartDto> registerHeartDtos;
    private List<RecipeReviewResponseDto> recipeReviewResponseDtos;
    private List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos;
    private List<UserRegisterDto> userRegisterDtos;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();
        age = user.getAge();
        profile = user.getProfile();
        recipeHeartDtos = new ArrayList<>();
        registerHeartDtos = new ArrayList<>();
//        recipeHeartDtos = user.getHearts().stream()
//                .filter(heart -> heart != null && heart.getRecipe() != null && heart.getRecipe().getRecipeId() != null) // null 값 필터링
//                .map(heart -> new RecipeHeartDto(heart))
//                .collect(Collectors.toList());
//        registerHeartDtos = user.getHearts().stream()
//                .filter(heart -> heart != null && heart.getRegisterRecipe() != null && heart.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
//                .map(heart -> new RegisterHeartDto(heart))
//                .collect(Collectors.toList());
        recipeReviewResponseDtos = new ArrayList<>();
        registerRecipeReviewResponseDtos = new ArrayList<>();
        userRegisterDtos = new ArrayList<>();
    }

    public static UserResponseDto createUserResponseDto(User user) {
        return new UserResponseDto(user);
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }
}
