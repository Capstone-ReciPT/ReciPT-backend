package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeHomeResponseDto;
import samdasu.recipt.domain.controller.dto.Recipe.RecipeShortResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterHomeResponseDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRecipeShortResponseDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.service.RecipeService;
import samdasu.recipt.domain.service.RegisterRecipeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static samdasu.recipt.domain.controller.constant.ControllerStandard.STANDARD;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryApiController {
    private final RecipeService recipeService;
    private final RegisterRecipeService registerRecipeService;

    @GetMapping()
    public Result1 homeInfo() {
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();
        if (registerRecipes.size() < STANDARD) {
            //레시피 리스트 뷰 (평점 순)
            List<Recipe> recipeLike = recipeService.findTop10RatingScore();
            Result1 top10List = getRecipeTop10List(recipeLike);

            return new Result1(recipeLike.size(), top10List);
        } else {
            //레시피 리스트 뷰 (평점 순)
            List<RegisterRecipe> registerRecipeLike = registerRecipeService.findTop10RatingScore();
            Result1 top10List = getRegisterTop10List(registerRecipeLike);

            return new Result1(registerRecipeLike.size(), top10List);
        }
    }

    @GetMapping("/recipes")
    public Result2 searchRecipesByCategory(@RequestParam(value = "category") String category) {
        List<Recipe> recipeCategory = recipeService.findByCategory(category);
        List<RecipeShortResponseDto> collect1 = getRecipesByCategory(recipeCategory);

        List<RegisterRecipe> registerRecipeCategory = registerRecipeService.findByCategory(category);
        List<RegisterRecipeShortResponseDto> collect2 = getRegisterRecipesByCategory(registerRecipeCategory);
        return new Result2(category, collect1.size(), collect2.size(), collect1, collect2);
    }

    private List<RecipeShortResponseDto> getRecipesByCategory(List<Recipe> recipeCategory) {
        List<RecipeShortResponseDto> collect1 = recipeCategory.stream()
                .map(RecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return collect1;
    }

    private List<RegisterRecipeShortResponseDto> getRegisterRecipesByCategory(List<RegisterRecipe> registerRecipeCategory) {
        List<RegisterRecipeShortResponseDto> collect2 = registerRecipeCategory.stream()
                .map(RegisterRecipeShortResponseDto::new)
                .collect(Collectors.toList());
        return collect2;
    }

    private Result1 getRecipeTop10List(List<Recipe> top10RecipeList) {
        Map<Integer, RecipeHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RecipeHomeResponseDto homeInfo = RecipeHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result1(collect.size(), collect);
    }

    private Result1 getRegisterTop10List(List<RegisterRecipe> top10RecipeList) {
        Map<Integer, RegisterHomeResponseDto> collect = new HashMap<>();
        for (int i = 0; i < top10RecipeList.size(); i++) {
            RegisterHomeResponseDto homeInfo = RegisterHomeResponseDto.CreateRecipeHomeResponseDto(top10RecipeList.get(i));
            collect.put(i, homeInfo);
        }
        return new Result1(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result1<T> {
        private int count; // 레시피 수
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private String category;
        private int recipeCount; // 레시피 수
        private int registerRecipeCount; // 레시피 수
        private T recipeList;
        private T registerRecipeList;
    }
}
