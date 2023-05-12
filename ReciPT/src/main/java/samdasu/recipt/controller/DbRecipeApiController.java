package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.controller.dto.Recipe.RecipeHomeResponseDto;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.service.RecipeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DbRecipeApiController {
    private final RecipeService recipeService;

    // 레시피 id값으로 조회
    @GetMapping("/db/show/{id}")
    public Result_1 showDbRecipeByRecipeId(@AuthenticationPrincipal UserResponseDto userResponseDto,
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
        return new Result_1(hearts.size(), reviews.size(), new RecipeResponseDto(findRecipe));
    }

    // 전체 레시피 간단 조회
    @GetMapping("db/show/list")
    public Result_2 showDbRecipeSimpleList() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeShortResponseDto> collect = findRecipes.stream()
                .map(m -> new RecipeShortResponseDto(m))
                .collect(Collectors.toList());
        return new Result_2(collect.size(), collect);
    }

    // 전체 레시피 조회
    @GetMapping("db/show/all")
    public Result_2 showDbRecipeAllList() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeResponseDto> collect = findRecipes.stream()
                .map(m -> new RecipeResponseDto(m))
                .collect(Collectors.toList());
        return new Result_2(collect.size(), collect);
    }

    // 좋아요 Top10
    @GetMapping("db/show/hot/like")
    public Result_2 showLikeDbRecipeList() {
        List<Recipe> findTop10RecipeLike = recipeService.findTop10RecipeLike();
        return getTop10List(findTop10RecipeLike);
    }

    // 조회수 Top10
    @GetMapping("db/show/hot/view")
    public Result_2 showViewDbRecipeList() {
        List<Recipe> findTop10RecipeView = recipeService.findTop10RecipeView();
        return getTop10List(findTop10RecipeView);
    }

    // 평점 Top10
    @GetMapping("db/show/hot/rate")
    public Result_2 showRateDbRecipeList() {
        List<Recipe> findTop10RecipeRatingScore = recipeService.findTop10RecipeRatingScore();
        return getTop10List(findTop10RecipeRatingScore);
    }

    private Result_2 getTop10List(List<Recipe> findTop10RecipeList) {
        Map<Integer, RecipeHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < findTop10RecipeList.size(); i++) {
            RecipeHomeResponseDto homeInfo = RecipeHomeResponseDto.CreateRecipeHomeResponseDto(findTop10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result_2(collect.size(), collect);
    }

    // 좋아요 증가
//    @PostMapping("db/update/like/{id}")
//    public Result_3 updateLikeDbRecipe(@PathVariable("id") Long recipeId, @RequestBody @Valid  ) {
//    }

    @Data
    @AllArgsConstructor
    static class Result_1<T> {
        private int heartCount; //특정 List의 개수
        private int reviewCount; //특정 List의 개수
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class Result_2<T> {
        private int recipeCount; // 레시피 수
        private T data;
    }
}
