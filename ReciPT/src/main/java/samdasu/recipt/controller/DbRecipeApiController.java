package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.service.RecipeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/db")
public class DbRecipeApiController {
    private final RecipeService recipeService;

    // 레시피 id값으로 조회
    @GetMapping("/{id}")
    public Result1 showDbRecipeByRecipeId(@AuthenticationPrincipal UserResponseDto userResponseDto,
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

    // 전체 레시피 간단 조회
    @GetMapping("/short")
    public Result2 showDbRecipeSimpleList() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeShortResponseDto> collect = findRecipes.stream()
                .map(RecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return new Result2(collect.size(), collect);
    }

    // 전체 레시피 조회
    @GetMapping("/all")
    public Result2 showDbRecipeAllList() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeResponseDto> collect = findRecipes.stream()
                .map(RecipeResponseDto::new)
                .collect(Collectors.toList());
        return new Result2(collect.size(), collect);
    }

    // 좋아요 증가
//    @PostMapping("db/update/like/{id}")
//    public Result3 updateLikeDbRecipe(@PathVariable("id") Long recipeId, @RequestBody @Valid  ) {
//    }

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
