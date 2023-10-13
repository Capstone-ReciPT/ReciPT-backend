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
import samdasu.recipt.domain.controller.dto.Register.UserRegisterDto;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.domain.entity.Heart;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.HeartService;
import samdasu.recipt.domain.service.RegisterRecipeService;
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

        UserResponseDto responseDto = new UserResponseDto(findUser);

        // 프로필 사진
        byte[] profile = uploadService.getUserProfile(responseDto.getUsername(), responseDto.getProfile());

        // 좋아요 정보
        List<RecipeHeartDto> recipeHeart = new ArrayList<>();
        List<RegisterHeartDto> registerHeart = new ArrayList<>();
        if (!findUser.getHearts().stream().collect(Collectors.toList()).isEmpty()){
           recipeHeart = findUser.getHearts().stream()
                    .filter(heart -> heart != null && heart.getRecipe() != null && heart.getRecipe().getRecipeId() != null) // null 값 필터링
                    .map(RecipeHeartDto::new)
                    .collect(Collectors.toList());

           responseDto.setRecipeHeartDtos(recipeHeart);

            registerHeart = findUser.getHearts().stream()
                    .filter(heart -> heart != null && heart.getRegisterRecipe() != null && heart.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
                    .map(RegisterHeartDto::new)
                    .collect(Collectors.toList());

            for (RegisterHeartDto registerHeartDto : registerHeart) {
                byte[] registerThumbnail = uploadService.getRegisterProfile(findUser.getUsername(), registerHeartDto.getThumbnailImage());
                registerHeartDto.setThumbnailImageByte(registerThumbnail);
                responseDto.getRegisterHeartDtos().add(registerHeartDto);
            }
        }

        //리뷰 정보
        List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos = new ArrayList<>();
        List<RecipeReviewResponseDto> recipeReviewResponseDtos  = new ArrayList<>();

        if (!findUser.getReviews().stream().collect(Collectors.toList()).isEmpty()){
            recipeReviewResponseDtos = findUser.getReviews().stream()
                    .filter(review -> review != null && review.getRecipe() != null && review.getRecipe().getRecipeId() != null)
                    .map(review -> new RecipeReviewResponseDto(review))
                    .collect(Collectors.toList());

            responseDto.setRecipeReviewResponseDtos(recipeReviewResponseDtos);
        }

        if (!findUser.getRegisterRecipes().stream().collect(Collectors.toList()).isEmpty()){
            registerRecipeReviewResponseDtos = findUser.getReviews().stream()
                    .filter(review -> review != null && review.getRegisterRecipe() != null && review.getRegisterRecipe().getRegisterId() != null)
                    .map(review -> new RegisterRecipeReviewResponseDto(review))
                    .collect(Collectors.toList());

            for (RegisterRecipeReviewResponseDto registerRecipeReviewResponseDto : registerRecipeReviewResponseDtos) {
                byte[] recipeThumbnail = uploadService.getRegisterProfile(findUser.getUsername(), registerRecipeReviewResponseDto.getThumbnailImage());
                registerRecipeReviewResponseDto.setThumbnailImageByte(recipeThumbnail);
                responseDto.getRegisterRecipeReviewResponseDtos().add(registerRecipeReviewResponseDto);
            }
        }

        //레시피 등록 정보
        List<UserRegisterDto> userRegisterDtos = findUser.getRegisterRecipes().stream()
                .map(registerRecipe -> new UserRegisterDto(registerRecipe))
                .collect(Collectors.toList());

        for (UserRegisterDto userRegisterDto : userRegisterDtos) {
            byte[] registerThumbnail = uploadService.getRegisterProfile(findUser.getUsername(), userRegisterDto.getThumbnailImage());
            userRegisterDto.setThumbnailImageByte(registerThumbnail);
            responseDto.getUserRegisterDtos().add(userRegisterDto);
        }

        return new Result1(recipeHeart.size() + registerHeart.size(), responseDto.getRecipeReviewResponseDtos().size() + responseDto.getRegisterRecipeReviewResponseDtos().size(),
                userRegisterDtos.size(), responseDto, profile);
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/user/edit")
    public Result2 updateUser(Authentication authentication,
                              @Valid UserUpdateRequestDto request) throws JsonProcessingException {
        User authenticationUser = userService.findUserByUsername(authentication.getName());
        Long updateUserId = userService.update(authenticationUser.getUserId(), request);

        User findUser = userService.findUserById(updateUserId);

        log.info("user.getPassword() = {}", findUser.getPassword());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] result = uploadService.getUserProfile(findUser.getUsername(),responseDto.getProfile());

        return new Result2(1, responseDto, result);
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


    @Data
    @AllArgsConstructor
    static class Result1<T> {
        private int heartCount;
        private int reviewCount;
        private int registerRecipeSize;
        private T data;
        private T profile;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
        private T profile;
    }
}

