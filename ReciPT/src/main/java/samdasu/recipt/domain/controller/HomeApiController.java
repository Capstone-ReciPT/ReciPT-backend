package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeHomeResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterHomeResponseDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.service.RecipeService;
import samdasu.recipt.domain.service.RegisterRecipeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static samdasu.recipt.domain.controller.constant.ControllerStandard.STANDARD;

/**
 * 리뷰 작성 필요
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeApiController {
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    @GetMapping("/home")
    public Result homeInfo() {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        if (registerRecipes.size() < STANDARD) {
            //인기 레세피 (좋아요 순)
            List<Recipe> recipeLike = recipeService.findTop10Like();
            Result2 top10Like = getRecipeTop10List(recipeLike);

            //오늘의 레시피 (조회수 순)
            List<Recipe> recipeView = recipeService.findTop10View();
            Result2 top10View = getRecipeTop10List(recipeView);

            //새로운 레시피(등록된지 24시간)
            List<Recipe> recipeRecent = recipeService.findTop10RecentRegister();
            Result2 top10Recent = getRecipeTop10List(recipeRecent);

            return new Result(top10Like, top10View, top10Recent);
        } else {
            //인기 레세피 (좋아요 순)
            List<RegisterRecipe> registerRecipeLike = registerRecipeService.findTop10Like();
            Result2 top10Like = getRegisterTop10List(registerRecipeLike);

            //오늘의 레시피 (조회수 순)
            List<RegisterRecipe> registerRecipeView = registerRecipeService.findTop10View();
            Result2 top10View = getRegisterTop10List(registerRecipeView);

            //새로운 레시피(등록된지 24시간)
            List<RegisterRecipe> registerRecipeRecent = registerRecipeService.findTop10RecentRegister();
            Result2 top10Recent = getRegisterTop10List(registerRecipeRecent);

            return new Result(top10Like, top10View, top10Recent);
        }
    }

    private Result2 getRecipeTop10List(List<Recipe> top10RecipeList) {
        Map<Integer, RecipeHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RecipeHomeResponseDto homeInfo = RecipeHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result2(collect.size(), collect);
    }

    private Result2 getRegisterTop10List(List<RegisterRecipe> top10RecipeList) {
        Map<Integer, RegisterHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RegisterHomeResponseDto homeInfo = RegisterHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result2(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T like;
        private T view;
        private T recent;

    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int recipeCount; // 레시피 수
        private T data;
    }

}
