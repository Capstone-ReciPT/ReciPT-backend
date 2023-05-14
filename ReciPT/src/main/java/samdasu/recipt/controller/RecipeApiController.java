package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.service.HeartService;
import samdasu.recipt.service.RecipeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리마다 매핑주소 주기
 */
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

    /**
     * 카테고리별로 나눠서 보이는게 맞을듯
     */
    @GetMapping("/all")
    public Result2 recipeDetailView() {
        List<Recipe> findRecipes = recipeService.findRecipes();
        List<RecipeResponseDto> collect = findRecipes.stream()
                .map(RecipeResponseDto::new)
                .collect(Collectors.toList());
        return new Result2(collect.size(), collect);
    }


    @PostMapping("/insert/{id}")
    public void insertHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        Recipe findRecipe = recipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRecipe.getRecipeId(), findRecipe.getFoodName(), findRecipe.getCategory(), findRecipe.getIngredient());
        heartService.insertRecipeHeart(recipeHeartDto);
    }

    @PostMapping("/cancel/{id}")
    public void deleteHeart(@AuthenticationPrincipal UserResponseDto userResponseDto,
                            @PathVariable("id") Long recipeId) {
        Recipe findRecipe = recipeService.findById(recipeId);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(userResponseDto.getUserId(), findRecipe.getRecipeId(), findRecipe.getFoodName(), findRecipe.getCategory(), findRecipe.getIngredient());
        heartService.deleteRecipeHeart(recipeHeartDto);
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
