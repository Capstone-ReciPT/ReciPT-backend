package samdasu.recipt.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Register.UserRegisterDto;
import samdasu.recipt.domain.controller.dto.User.UserReissueDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.domain.entity.Heart;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.Review;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.HeartService;
import samdasu.recipt.domain.service.RegisterRecipeService;
import samdasu.recipt.domain.service.ReviewService;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.utils.Image.UploadService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;
    private final UploadService uploadService;
    private final RegisterRecipeService registerRecipeService;
    private final ReviewService reviewService;
    private final HeartService heartService;

    @Value("${image.register.path}")
    private String registerImage;

    /**
     * 프로필 보기
     */
    @GetMapping("/user")
    public Result1 userInfo(Authentication authentication) {
        User findUser = userService.findUserByUsername(authentication.getName());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getLoginId() = {}", findUser.getLoginId());
        log.info("user.getPassword() = {}", findUser.getPassword());
        log.info("user.getAge() = {}", findUser.getAge());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] result = uploadService.getUserProfile(responseDto.getUsername(), responseDto.getProfile());

        return new Result1(1, responseDto, result);
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/user/edit")
    public Result1 updateUser(Authentication authentication,
                              @Valid UserUpdateRequestDto request) throws JsonProcessingException {
        User authenticationUser = userService.findUserByUsername(authentication.getName());
        Long updateUserId = userService.update(authenticationUser.getUserId(), request);

        User findUser = userService.findUserById(updateUserId);

        log.info("user.getPassword() = {}", findUser.getPassword());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] result = uploadService.getUserProfile(findUser.getUsername(),responseDto.getProfile());

        return new Result1(1, responseDto, result);
    }

    /**
     * 유저가 누른 좋아요 보기
     */

    @GetMapping("/user/like")
    public Result2 searchLikeInfo(Authentication authentication) {
        User findUser = userService.findUserByUsername(authentication.getName());
        UserResponseDto responseDto = new UserResponseDto(findUser);

        List<RecipeHeartDto> recipeHeart = findUser.getHearts().stream()
                .filter(heart -> heart != null && heart.getRecipe() != null && heart.getRecipe().getRecipeId() != null) // null 값 필터링
                .map(RecipeHeartDto::new)
                .collect(Collectors.toList());
        List<RegisterHeartDto> registerHeart = findUser.getHearts().stream()
                .filter(heart -> heart != null && heart.getRegisterRecipe() != null && heart.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
                .map(RegisterHeartDto::new)
                .collect(Collectors.toList());

        return new Result2(recipeHeart.size() + registerHeart.size(), responseDto);
    }

    /**
     * 유저가 등록한 레시피 보기
     */
    @GetMapping("/user/register")
    public Result3 searchRegisterInfo(Authentication authentication) {
        User findUser = userService.findUserByUsername(authentication.getName());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] profile = uploadService.getUserProfile(findUser.getUsername(), responseDto.getProfile());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getRegisterRecipes.getFoodName() = {}", findUser.getRegisterRecipes().stream()
                .map(RegisterRecipe::getFoodName).collect(Collectors.toList()));

        List<UserRegisterDto> collect = findUser.getRegisterRecipes().stream()
                .map(registerRecipe -> new UserRegisterDto(registerRecipe))
                .collect(Collectors.toList());


        List<byte[]> registerFoodProfileList = new ArrayList<>();
        for (UserRegisterDto userRegisterDto : collect) {
            registerFoodProfileList.add(uploadService.getRegisterProfile(findUser.getUsername(), userRegisterDto.getThumbnailImage()));
        }

        return new Result3(collect.size(), responseDto, profile, registerFoodProfileList);
    }

    /**
     * 등록된 레시피 삭제
     */
    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteRecipe(Authentication authentication, @RequestParam(value = "registerRecipeId") Long registerRecipeId) {
        boolean isDelete = false;
        User findUser = userService.findUserByUsername(authentication.getName());
        List<RegisterRecipe> registerRecipes = findUser.getRegisterRecipes();

        for (RegisterRecipe registerRecipe : registerRecipes) {
            if (registerRecipe.getRegisterId().equals(registerRecipeId)) {
                isDelete = true;
                List<Heart> hearts = registerRecipe.getHearts();
                for (Heart heart : hearts) {
                    RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(heart);
                    heartService.deleteRegisterRecipeHeart(registerHeartDto);
                }
                registerRecipeService.deleteRegisterRecipe(registerRecipeId);
                return ResponseEntity.status(OK).body("레시피 삭제 완료!");
            }
        }
        return status(FORBIDDEN).body("삭제 권한이 없습니다!");
    }

    /**
     * 유저가 작성한 리뷰 보기
     */
    @GetMapping("/user/review")
    public Result1 searchReviewInfo(Authentication authentication) {
        User findUser = userService.findUserByUsername(authentication.getName());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] result = uploadService.getUserProfile(findUser.getUsername(),responseDto.getProfile());

        List<Review> reviews = reviewService.findReviewByWriter(findUser.getUsername());

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getReviews.getComment() = {}", findUser.getReviews().stream()
                .map(Review::getComment).collect(Collectors.toList()));

        return new Result1(reviews.size(), responseDto, result);
    }

    @Data
    @AllArgsConstructor
    static class Result1<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
        private T profile;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class Result3<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
        private T profile;
        private T registerProfile;
    }
}

