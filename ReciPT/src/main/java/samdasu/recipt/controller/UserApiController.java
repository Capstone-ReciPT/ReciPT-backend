package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.controller.dto.User.*;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto saveUser(@Valid UserSignUpDto userSignUpDto) {
        Long joinUserId = userService.join(userSignUpDto);

        User findUser = userService.findById(joinUserId);
        return new UserResponseDto(findUser);
    }

    /**
     * 프로필 보기
     */
    @GetMapping("/user/profile")
    public Result userInfo(@AuthenticationPrincipal UserInfoResponseDto userInfoResponseDto) {
        User findUser = userService.findById(userInfoResponseDto.getUserId());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getProfile() = {}", findUser.getProfile());
        log.info("user.getLoginId() = {}", findUser.getLoginId());
        log.info("user.getPassword() = {}", findUser.getPassword());
        log.info("user.getAge() = {}", findUser.getAge());

        return new Result(1, new UserResponseDto(findUser));
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/user/edit")
    public Result updateUser(@AuthenticationPrincipal UserInfoResponseDto userInfoResponseDto,
                             @RequestBody @Valid UserUpdateRequestDto request) {

        userService.update(userInfoResponseDto.getUserId(), request);
        User findUser = userService.findById(userInfoResponseDto.getUserId());

        log.info("user.getPassword() = {}", findUser.getPassword());
        log.info("user.getProfile() = {}", findUser.getProfile());

        return new Result(1, new UserInfoResponseDto(findUser));
    }

    /**
     * 유저가 누른 좋아요 보기
     */
    @GetMapping("/user/like")
    public Result searchLikeInfo(@AuthenticationPrincipal UserLikeResponseDto userLikeResponseDto) {
        User findUser = userService.findById(userLikeResponseDto.getUserId());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.RecipeHeartDto() = {}", findUser.getHearts().stream()
                .map(RecipeHeartDto::new).collect(Collectors.toList()));
        log.info("user.RegisterHeartDto() = {}", findUser.getHearts().stream()
                .map(RegisterHeartDto::new).collect(Collectors.toList()));


        List<RecipeHeartDto> recipeHeart = findUser.getHearts().stream()
                .map(RecipeHeartDto::new)
                .collect(Collectors.toList());
        List<RegisterHeartDto> registerHeart = findUser.getHearts().stream()
                .map(RegisterHeartDto::new)
                .collect(Collectors.toList());


        return new Result(recipeHeart.size() + registerHeart.size(), new UserLikeResponseDto(findUser));
    }

    /**
     * 유저가 등록한 레시피 보기
     */
    @GetMapping("/user/register")
    public Result searchRegisterInfo(@AuthenticationPrincipal UserRegisterResponseDto userRegisterResponseDto) {
        User findUser = userService.findById(userRegisterResponseDto.getUserId());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getRegisterRecipes.getFoodName() = {}", findUser.getRegisterRecipes().stream()
                .map(RegisterRecipe::getFoodName).collect(Collectors.toList()));

        List<RegisterResponseDto> collect = findUser.getRegisterRecipes().stream()
                .map(RegisterResponseDto::new)
                .collect(Collectors.toList());

        return new Result(collect.size(), new UserRegisterResponseDto(findUser));
    }

    /**
     * 유저가 작성한 리뷰 보기
     */
    @GetMapping("/user/review")
    public Result searchRegisterInfo(@AuthenticationPrincipal UserReviewResponseDto userReviewResponseDto) {
        User findUser = userService.findById(userReviewResponseDto.getUserId());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getReviews.getComment() = {}", findUser.getReviews().stream()
                .map(Review::getComment).collect(Collectors.toList()));

        List<RecipeReviewResponseDto> recipeReviewResponseDtos = findUser.getReviews().stream()
                .map(RecipeReviewResponseDto::new)
                .collect(Collectors.toList());

        List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos = findUser.getReviews().stream()
                .map(RegisterRecipeReviewResponseDto::new)
                .collect(Collectors.toList());

        return new Result(recipeReviewResponseDtos.size() + registerRecipeReviewResponseDtos.size(), new UserReviewResponseDto(findUser));
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }
}

