package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.service.RecipeService;
import samdasu.recipt.service.RegisterRecipeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 인기 레세피 (좋아요 순)
 * 오늘의 레시피 (조회수 순)
 * 새로운 레시피(등록된지 24시간)
 * 검색 기능
 * - 처음에는 레시피에서만 인기, 오늘의 레시피, 새로운 레시피 보여주다가 나중에 레시피 등록한게 10개 이상 쌓이면 레시피 등록한것만 보여주게끔 change?
 * <p>
 * postmapping
 * yolo
 * gpt
 * 레시피 쓰기
 * 리뷰 작성
 * <p>
 * 홈
 * 레시피
 * 음식 추천
 * 마이페이지
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
        if (registerRecipes.size() < 10) {
            //인기 레세피 (좋아요 순)
            List<Recipe> recipeLike = recipeService.findTop10Like();

            List<String> popularThumbnail = new ArrayList<>();
            List<String> popularFoodName = new ArrayList<>();
            for (int i = 0; i < recipeLike.size(); i++) {
                RecipeResponseDto recipeResponseDto = RecipeResponseDto.createRecipeResponseDto(recipeLike.get(i));

                popularThumbnail.add(recipeResponseDto.getThumbnailImage());
                popularFoodName.add(recipeResponseDto.getFoodName());
            }

            //오늘의 레시피 (조회수 순)
            List<Recipe> recipeView = recipeService.findTop10View();

            List<String> todayThumbnail = new ArrayList<>();
            List<String> todayFoodName = new ArrayList<>();
            for (int i = 0; i < recipeView.size(); i++) {
                RecipeResponseDto recipeResponseDto = RecipeResponseDto.createRecipeResponseDto(recipeView.get(i));

                todayThumbnail.add(recipeResponseDto.getThumbnailImage());
                todayFoodName.add(recipeResponseDto.getFoodName());
            }


            //새로운 레시피(등록된지 24시간)
            List<Recipe> recipeRecent = recipeService.findTop10RecentRegister();

            List<String> recentThumbnail = new ArrayList<>();
            List<String> recentFoodName = new ArrayList<>();
            for (int i = 0; i < recipeRecent.size(); i++) {
                RecipeResponseDto recipeResponseDto = RecipeResponseDto.createRecipeResponseDto(recipeRecent.get(i));

                recentThumbnail.add(recipeResponseDto.getThumbnailImage());
                recentFoodName.add(recipeResponseDto.getFoodName());
            }

            return new Result(popularThumbnail, popularFoodName, todayThumbnail, todayFoodName, recentThumbnail, recentFoodName);
        } else {
            //인기 레세피 (좋아요 순)
            List<RegisterRecipe> registerRecipeLike = registerRecipeService.findTop10Like();

            List<byte[]> popularThumbnail = new ArrayList<>();
            List<String> popularFoodName = new ArrayList<>();
            for (int i = 0; i < registerRecipeLike.size(); i++) {
                RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipeLike.get(i));

                popularThumbnail.add(registerResponseDto.getThumbnailImage());
                popularFoodName.add(registerResponseDto.getTitle());
            }

            //오늘의 레시피 (조회수 순)
            List<RegisterRecipe> registerRecipeView = registerRecipeService.findTop10View();

            List<byte[]> todayThumbnail = new ArrayList<>();
            List<String> todayFoodName = new ArrayList<>();
            for (int i = 0; i < registerRecipeLike.size(); i++) {
                RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipeLike.get(i));

                todayThumbnail.add(registerResponseDto.getThumbnailImage());
                todayFoodName.add(registerResponseDto.getFoodName());
            }


            //새로운 레시피(등록된지 24시간)
            List<RegisterRecipe> registerRecipeRecent = registerRecipeService.findTop10RecentRegister();

            List<byte[]> recentThumbnail = new ArrayList<>();
            List<String> recentFoodName = new ArrayList<>();
            for (int i = 0; i < registerRecipeRecent.size(); i++) {
                RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipeRecent.get(i));

                recentThumbnail.add(registerResponseDto.getThumbnailImage());
                recentFoodName.add(registerResponseDto.getFoodName());
            }

            return new Result(popularThumbnail, popularFoodName, todayThumbnail, todayFoodName, recentThumbnail, recentFoodName);
        }
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data1;
        private T data2;
        private T data3;
        private T data4;
        private T data5;
        private T data6;
    }

}
