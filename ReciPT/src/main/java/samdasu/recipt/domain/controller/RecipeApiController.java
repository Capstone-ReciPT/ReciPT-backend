package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.service.HeartService;
import samdasu.recipt.domain.service.RecipeService;

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
@RequestMapping("/api/db")
public class RecipeApiController {
    private final RecipeService recipeService;

    private final HeartService heartService;

    @GetMapping("/{id}")
    public Result1 eachRecipeInfo(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                  @PathVariable("id") Long recipeId) {
        //조회수 증가
        recipeService.IncreaseViewCount(recipeId);

        //db 레시피 조회
        Recipe findRecipe = recipeService.findById(recipeId);
        RecipeResponseDto recipeResponseDto = RecipeResponseDto.createRecipeResponseDto(findRecipe);

        List<RecipeResponseDto> hearts = findRecipe.getHearts().stream()
                .map(heart -> recipeResponseDto)
                .collect(Collectors.toList());
        List<RecipeResponseDto> reviews = findRecipe.getReview().stream()
                .map(review -> recipeResponseDto)
                .collect(Collectors.toList());
        return new Result1(hearts.size(), reviews.size(), new RecipeResponseDto(findRecipe));
    }

    @GetMapping("/short")
    public Result2 recipeSimpleView() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeShortResponseDto> collect = findRecipes.stream()
                .map(RecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return new Result2(collect.size(), collect);
    }

    @PostMapping("/insert/{id}")
    public void insertHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        Recipe findRecipe = recipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRecipe.getRecipeId(), findRecipe.getFoodName(), findRecipe.getCategory(), findRecipe.getIngredient());
        heartService.insertRecipeHeart(recipeHeartDto);
        log.info("좋아요 추가 성공");
    }

    @PostMapping("/cancel/{id}")
    public void deleteHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        Recipe findRecipe = recipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRecipe.getRecipeId(), findRecipe.getFoodName(), findRecipe.getCategory(), findRecipe.getIngredient());
        heartService.deleteRecipeHeart(recipeHeartDto);
        log.info("좋아요 삭제 성공");
    }

    /**
     * reviewRequestDto 수정 가능성 있음
     */
    @PostMapping("/update/{id}")
    public void updateRatingScore(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                  @PathVariable("id") Long recipeId, @Valid ReviewRequestDto requestDto) {
        recipeService.updateRatingScore(recipeId, requestDto);
    }

    //    @Scheduled(cron = "*/3 * * * * *")
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void resetView() {
        log.info("Reset ViewCount Complete!!");
        recipeService.resetViewCount();
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