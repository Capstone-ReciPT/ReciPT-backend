package samdasu.recipt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.controller.dto.User.UserSignUpDto;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.service.HeartService;
import samdasu.recipt.service.ProfileService;
import samdasu.recipt.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    private final ProfileService profileService;

    @PostMapping("/signup")
    public Result1 saveUser(@Valid UserSignUpDto userSignUpDto, @RequestParam(value = "profile") MultipartFile file) throws IOException {
        Long savedProfileId = profileService.uploadImage(file);
        Long joinUserId = userService.join(userSignUpDto, savedProfileId);

        User findUser = userService.findById(joinUserId);
        log.info("findUser.getProfile().getFilename() = {}", findUser.getProfile().getFilename());
        log.info("findUser.getUsername = {}", findUser.getUsername());

        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        return new Result1(1, new UserResponseDto(findUser), ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage));
    }

    /**
     * 프로필 보기
     */
    @GetMapping("/user")
    public Result1 userInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) throws JsonProcessingException {
        User findUser = userService.findById(userResponseDto.getUserId());
        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getLoginId() = {}", findUser.getLoginId());
        log.info("user.getPassword() = {}", findUser.getPassword());
        log.info("user.getAge() = {}", findUser.getAge());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        // JSON 데이터와 이미지 데이터를 하나의 JSON 객체에 담음
        Map<String, Object> data = new HashMap<>();
        data.put("image", downloadImage);

        // JSON 객체를 byte[]로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = objectMapper.writeValueAsBytes(data);

        // HTTP 응답 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonData.length);

        return new Result1(1, responseDto, new ResponseEntity<>(jsonData, headers, HttpStatus.OK));
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/user/edit")
    public Result1 updateUser(@AuthenticationPrincipal UserResponseDto userResponseDto,
                              @Valid UserUpdateRequestDto request) throws JsonProcessingException {
        userService.update(userResponseDto.getUserId(), request);

        User findUser = userService.findById(userResponseDto.getUserId());
        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        log.info("user.getPassword() = {}", findUser.getPassword());

        UserResponseDto responseDto = new UserResponseDto(findUser);
        // JSON 데이터와 이미지 데이터를 하나의 JSON 객체에 담음
        Map<String, Object> data = new HashMap<>();
        data.put("image", downloadImage);

        // JSON 객체를 byte[]로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = objectMapper.writeValueAsBytes(data);

        // HTTP 응답 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(jsonData.length);

        return new Result1(1, responseDto, new ResponseEntity<>(jsonData, headers, HttpStatus.OK));
    }

    /**
     * 유저가 누른 좋아요 보기
     */

    private final HeartService heartService;

    @GetMapping("/user/like")
    public Result2 searchLikeInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        User findUser = userService.findById(userResponseDto.getUserId());

//        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(findUser.getUserId(), 3L, "된장 부대찌개");
//        RecipeHeartDto recipeHeartDto1 = RecipeHeartDto.createRecipeHeartDto(findUser.getUserId(), 10L, "구운채소");
//        RecipeHeartDto recipeHeartDto2 = RecipeHeartDto.createRecipeHeartDto(findUser.getUserId(), 9L, "함초 냉이 국수");
//        heartService.insertRecipeHeart(recipeHeartDto);
//        heartService.insertRecipeHeart(recipeHeartDto1);
//        heartService.insertRecipeHeart(recipeHeartDto2);

        UserResponseDto responseDto = new UserResponseDto(findUser);
//        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        List<RecipeHeartDto> recipeHeart = findUser.getHearts().stream()
                .filter(heart -> heart != null && heart.getRecipe() != null && heart.getRecipe().getRecipeId() != null) // null 값 필터링
                .map(RecipeHeartDto::new)
                .collect(Collectors.toList());
        List<RegisterHeartDto> registerHeart = findUser.getHearts().stream()
                .filter(heart -> heart != null && heart.getRegisterRecipe() != null && heart.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
                .map(RegisterHeartDto::new)
                .collect(Collectors.toList());


//        return new Result(recipeHeart.size() + registerHeart.size(), responseDto, ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(downloadImage));

        return new Result2(recipeHeart.size() + registerHeart.size(), responseDto);
    }

    /**
     * 유저가 등록한 레시피 보기
     */
    @GetMapping("/user/register")
    public Result1 searchRegisterInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        User findUser = userService.findById(userResponseDto.getUserId());
        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getRegisterRecipes.getFoodName() = {}", findUser.getRegisterRecipes().stream()
                .map(RegisterRecipe::getFoodName).collect(Collectors.toList()));

        List<RegisterResponseDto> collect = findUser.getRegisterRecipes().stream()
                .map(RegisterResponseDto::new)
                .collect(Collectors.toList());

        return new Result1(collect.size(), responseDto, ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage));
    }

    /**
     * 유저가 작성한 리뷰 보기
     */
    @GetMapping("/user/review")
    public Result1 searchReviewInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        User findUser = userService.findById(userResponseDto.getUserId());
        UserResponseDto responseDto = new UserResponseDto(findUser);
        byte[] downloadImage = profileService.downloadImage(findUser.getProfile().getProfileId()); //프로필 사진

        log.info("user.getUsername() = {}", findUser.getUsername());
        log.info("user.getReviews.getComment() = {}", findUser.getReviews().stream()
                .map(Review::getComment).collect(Collectors.toList()));

        List<RecipeReviewResponseDto> recipeReviewResponseDtos = findUser.getReviews().stream()
                .filter(review -> review != null && review.getRecipe() != null && review.getRecipe().getRecipeId() != null) // null 값 필터링
                .map(RecipeReviewResponseDto::new)
                .collect(Collectors.toList());

        List<RegisterRecipeReviewResponseDto> registerRecipeReviewResponseDtos = findUser.getReviews().stream()
                .filter(review -> review != null && review.getRegisterRecipe() != null && review.getRegisterRecipe().getRegisterId() != null) // null 값 필터링
                .map(RegisterRecipeReviewResponseDto::new)
                .collect(Collectors.toList());

        return new Result1(recipeReviewResponseDtos.size() + registerRecipeReviewResponseDtos.size(), responseDto, ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage));
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

}

