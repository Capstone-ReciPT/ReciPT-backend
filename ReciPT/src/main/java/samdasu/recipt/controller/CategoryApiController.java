package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.Recipe.RecipeHomeResponseDto;
import samdasu.recipt.controller.dto.Register.RegisterHomeResponseDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.service.RecipeService;
import samdasu.recipt.service.RegisterRecipeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 카테고리별로 나눠서 보이기
 * - @RequestParam
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryApiController {
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    @GetMapping("/category")
    public Result homeInfo() {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        if (registerRecipes.size() < 10) {
            //레시피 리스트 뷰 (평점 순)
            List<Recipe> recipeLike = recipeService.findTop10RatingScore();
            Result top10List = getRecipeTop10List(recipeLike);

            return new Result(recipeLike.size(), top10List);
        } else {
            //레시피 리스트 뷰 (평점 순)
            List<RegisterRecipe> registerRecipeLike = registerRecipeService.findTop10RatingScore();
            Result top10List = getRegisterTop10List(registerRecipeLike);

            return new Result(registerRecipeLike.size(), top10List);
        }
    }

    private Result getRecipeTop10List(List<Recipe> top10RecipeList) {
        Map<Integer, RecipeHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RecipeHomeResponseDto homeInfo = RecipeHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result(collect.size(), collect);
    }

    private Result getRegisterTop10List(List<RegisterRecipe> top10RecipeList) {
        Map<Integer, RegisterHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RegisterHomeResponseDto homeInfo = RegisterHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result(collect.size(), collect);
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; // 레시피 수
        private T data;
    }
}
