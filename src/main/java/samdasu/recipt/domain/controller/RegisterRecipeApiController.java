package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.*;
import samdasu.recipt.utils.Image.UploadService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterRecipeApiController {
    private final UserService userService;
    private final RegisterRecipeService registerRecipeService;
    private final UploadService uploadService;
    private final HeartService heartService;
    private final ReviewService reviewService;
    private final GptService gptService;

    @GetMapping("/find") //req로 foodname주면 response로 user가 gpt 테이블에 저장한 레시피의 foodName 줌
    public Result2 findGptRecipes(Authentication authentication
                                  , @RequestParam(value = "foodName") String foodName) {
        User findUser = userService.findUserByUsername(authentication.getName());
        List<String> findGptRecipes = gptService.getGptRecipesByUserId(foodName, findUser.getUserId());

        return new Result2(findGptRecipes.size(), findGptRecipes);
    }

    @PostMapping("/save/gpt") //req로 foodName주면 response로 (썸네일 바이트파일, 제목, 설명, 카테고리, 재료 (리스트), 레시피설명(단계별 리스트), 레시피 사진(단계별 리스트)) 줌
    public Result4 saveRecipeByGpt(Authentication authentication,
                              @RequestParam(value = "thumbnail") MultipartFile file,
                              @RequestParam(value = "images") MultipartFile[] files,
                              @Valid RegisterRequestDto requestDto) {
        User findUser = userService.findUserByUsername(authentication.getName());
        Long registerRecipeSave = registerRecipeService.registerRecipeSaveByGpt(findUser.getUserId(), file, files, requestDto.getFoodName(), requestDto);

        RegisterRecipe findRegister = registerRecipeService.findById(registerRecipeSave);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(findRegister);

        log.info("registerResponseDto.getThumbnailImage() = {}", registerResponseDto.getThumbnailImage());

        log.info("registerResponseDto.getLastModifiedDate() = {}", registerResponseDto.getLastModifiedDate());
        List<byte[]> thumbnail = new ArrayList<>();
        thumbnail.add(uploadService.getRegisterProfile(findRegister.getUser().getUsername(), registerResponseDto.getThumbnailImage()));

        List<byte[]> imageFiles = new ArrayList<>();
        int filesCnt = registerResponseDto.getImages().size();
        for (int i = 0; i < filesCnt; i++) {
            imageFiles.add(uploadService.getRegisterProfile(findRegister.getUser().getUsername(), registerResponseDto.getImages().get(i)));
        }
        return new Result4(new RegisterResponseDto(findRegister), thumbnail, imageFiles);
    }

    @PostMapping("/save/typing")
    public Result4 saveRecipeByTyping(Authentication authentication,
                              @RequestParam(value = "ingredients") String[] ingredients,
                              @RequestParam(value = "contexts") String[] contexts,
                              @RequestParam(value = "thumbnail") MultipartFile file,
                              @RequestParam(value = "images") MultipartFile[] files,
                              @Valid RegisterRequestDto requestDto) {
        User findUser = userService.findUserByUsername(authentication.getName());
        Long registerRecipeSave = registerRecipeService.registerRecipeSaveByTyping(findUser.getUserId(), file, files, ingredients, contexts, requestDto);

        RegisterRecipe findRegister = registerRecipeService.findById(registerRecipeSave);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(findRegister);

        log.info("registerResponseDto.getThumbnailImage() = {}", registerResponseDto.getThumbnailImage());

        log.info("registerResponseDto.getLastModifiedDate() = {}", registerResponseDto.getLastModifiedDate());
        List<byte[]> thumbnail = new ArrayList<>();
        thumbnail.add(uploadService.getRegisterProfile(findRegister.getUser().getUsername(), registerResponseDto.getThumbnailImage()));

        List<byte[]> imageFiles = new ArrayList<>();
        int filesCnt = registerResponseDto.getImages().size();
        for (int i = 0; i < filesCnt; i++) {
            imageFiles.add(uploadService.getRegisterProfile(findRegister.getUser().getUsername(), registerResponseDto.getImages().get(i)));
        }

        return new Result4(new RegisterResponseDto(findRegister), thumbnail, imageFiles);
    }

    @GetMapping("/{id}")
    public Result3 eachRecipeInfo(Authentication authentication, @PathVariable("id") Long recipeId) {
        Boolean heartCheck = false;
        User findUser = userService.findUserByUsername(authentication.getName());
        //좋아요 상태 판별
        heartCheck = heartService.checkRegisterRecipeHeart(findUser.getUserId(), recipeId);

        //조회수 증가
        registerRecipeService.increaseViewCount(recipeId);

        //등록된 레시피 조회
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(findRegisterRecipe);

        List<RegisterResponseDto> hearts = findRegisterRecipe.getHearts().stream()
                .map(heart -> registerResponseDto)
                .collect(Collectors.toList());
        List<RegisterResponseDto> reviews = findRegisterRecipe.getReviews().stream()
                .map(review -> registerResponseDto)
                .collect(Collectors.toList());
        return new Result3(heartCheck, hearts.size(), reviews.size(), new RegisterResponseDto(findRegisterRecipe));
    }

    @GetMapping("/short")
    public Result2 recipeSimpleView() {
        List<RegisterRecipe> findRecipes = registerRecipeService.findRegisterRecipes();
        List<RegisterRecipeShortResponseDto> collect = findRecipes.stream()
                .map(RegisterRecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return new Result2(collect.size(), collect);
    }

    @PostMapping("/insert/{id}")
    public Result1 insertHeart(Authentication authentication, @PathVariable("id") Long recipeId) {
        User findUser = userService.findUserByUsername(authentication.getName());
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(findUser.getUserId(), findRegisterRecipe.getRegisterId(), findRegisterRecipe.getFoodName(), findRegisterRecipe.getCategory(), findRegisterRecipe.getIngredient());
        heartService.insertRegisterRecipeHeart(registerHeartDto);
        return new Result1(findRegisterRecipe.getHearts().size());
    }

    @PostMapping("/cancel/{id}")
    public Result1 deleteHeart(Authentication authentication,
                               @PathVariable("id") Long recipeId) {
        User findUser = userService.findUserByUsername(authentication.getName());
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(findUser.getUserId(), findRegisterRecipe.getRegisterId(), findRegisterRecipe.getFoodName(), findRegisterRecipe.getCategory(), findRegisterRecipe.getIngredient());
        heartService.deleteRegisterRecipeHeart(registerHeartDto);
        return new Result1(findRegisterRecipe.getHearts().size());
    }

    /**
     * 리뷰 저장 + 평점 갱신
     */
    @PostMapping("/save/review/{id}")
    public void saveReview(Authentication authentication,
                           @PathVariable("id") Long recipeId, @RequestBody @Valid ReviewRequestDto requestDto) {
        User findUser = userService.findUserByUsername(authentication.getName());
        reviewService.saveRegisterRecipeReview(findUser.getUserId(), recipeId, requestDto);
        registerRecipeService.updateRatingScore(recipeId, requestDto);
    }

    //    @Scheduled(cron = "*/3 * * * * *")
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void resetView() {
        log.info("Reset ViewCount Complete!!");
        registerRecipeService.resetViewCount();
    }

    @Data
    @AllArgsConstructor
    static class Result1<T> {
        private int heartCount;
    }
    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private T data1;
        private T data2;
    }
    @Data
    @AllArgsConstructor
    static class Result3<T> {
        private Boolean heartCheck;
        private int heartCount;
        private int reviewCount;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class Result4<T> {
        private T registerRecipe;
        private T thumbnail;
        private T images;
    }
}
