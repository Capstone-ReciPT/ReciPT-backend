package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.service.HeartService;
import samdasu.recipt.domain.service.RegisterRecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * /short 대신 카테고리별로 /short
 * 평점 갱신 테스트 안해봄
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterRecipeApiController {
    private final RegisterRecipeService registerRecipeService;
    private final HeartService heartService;

    @PostMapping("/save")
    public Result2 saveRecipe(@AuthenticationPrincipal UserResponseDto userResponseDto
            , @RequestParam(value = "imageId") Long imageId
            , @RequestParam(value = "gptId") Long gptId
            , @RequestParam(value = "thumbnailId") Long thumbnailId
            , @Valid RegisterRequestDto requestDto) {
        Long registerRecipeSave = registerRecipeService.registerRecipeSave(userResponseDto.getUserId(), imageId, gptId, thumbnailId, requestDto);

        RegisterRecipe findRegister = registerRecipeService.findById(registerRecipeSave);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(findRegister);

        List<RegisterResponseDto> images = findRegister.getImageFiles().stream()
                .map(imageFile -> registerResponseDto)
                .collect(Collectors.toList());

        return new Result2(images.size(), new RegisterResponseDto(findRegister));
    }

    @GetMapping("/{id}")
    public Result1 eachRecipeInfo(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                  @PathVariable("id") Long recipeId) {
        //조회수 증가
        registerRecipeService.IncreaseViewCount(recipeId);

        //등록된 레시피 조회
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(findRegisterRecipe);

        List<RegisterResponseDto> hearts = findRegisterRecipe.getHearts().stream()
                .map(heart -> registerResponseDto)
                .collect(Collectors.toList());
        List<RegisterResponseDto> reviews = findRegisterRecipe.getReviews().stream()
                .map(review -> registerResponseDto)
                .collect(Collectors.toList());
        return new Result1(hearts.size(), reviews.size(), new RegisterResponseDto(findRegisterRecipe));
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
    public void insertHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRegisterRecipe.getRegisterId(), findRegisterRecipe.getFoodName(), findRegisterRecipe.getCategory(), findRegisterRecipe.getIngredient());
        heartService.insertRecipeHeart(recipeHeartDto);
    }

    @PostMapping("/cancel/{id}")
    public void deleteHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        RegisterRecipe findRegisterRecipe = registerRecipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRegisterRecipe.getRegisterId(), findRegisterRecipe.getFoodName(), findRegisterRecipe.getCategory(), findRegisterRecipe.getIngredient());
        heartService.deleteRecipeHeart(recipeHeartDto);
    }

    /**
     * reviewRequestDto 수정 가능성 있음
     */
    @PostMapping("/update/{id}")
    public void updateRatingScore(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                  @PathVariable("id") Long recipeId, @Valid ReviewRequestDto requestDto) {
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
        private int reviewCount;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int recipeCount;
        private T data;
    }
}