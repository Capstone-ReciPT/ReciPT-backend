package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.Recipe.RecipeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    /**
     * 평점 평균 계산
     */
    @Transactional
    public RecipeResponseDto updateRatingScore(Long recipeId, ReviewRequestDto reviewRequestDto) { //리뷰에서 하달받기
        Recipe recipe = findById(recipeId);
        recipe.updateRating(reviewRequestDto.getInputRatingScore());

        recipe.calcRatingScore(recipe);

        RecipeResponseDto recipeResponseDto = RecipeResponseDto.createRecipeResponseDto(recipe);

        return recipeResponseDto;
    }

    public Recipe findByFoodName(String foodName) {
        return recipeRepository.findByFoodName(foodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No Recipe Info"));
    }

    /**
     * 음식명 포함 조회(like '%foodName%')
     */
    public List<Recipe> findRecipeByContain(String searchingFoodName) {
        return recipeRepository.findRecipeByContain(searchingFoodName);
    }

    /**
     * 사용자 입력 값 보다 높은 좋아요 찾기
     */
    public List<Recipe> findSearchingRecipeLikeByInputNum(int inputNum) {
        return recipeRepository.SearchingRecipeLikeByInputNum(inputNum);
    }

    /**
     * 사용자 입력 값 보다 높은 조회수 찾기
     */
    public List<Recipe> findSearchingRecipeViewCountByInputNum(int inputNum) {
        return recipeRepository.SearchingRecipeViewCountByInputNum(inputNum);
    }

    @Transactional
    public void IncreaseViewCount(Long recipeId) {
        Recipe recipe = findById(recipeId);
        recipeRepository.addRecipeViewCount(recipe);
    }

    public List<Recipe> findRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe findById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Recipe Info"));
    }


//    /**
//     * 조회 수 탑 10 조회
//     */
//    public List<DbRecipe> findTop10ViewCount() {
//        return dbRecipeRepository.Top10DbRecipeView();
//    }
//
//    /**
//     * 좋아요 탑 10 조회
//     */
//    public List<DbRecipe> findTop10LikeCount() {
//        return dbRecipeRepository.Top10DbRecipeLike();
//    }
//
//    /**
//     * 좋아요 탑 1
//     */
//    public DbRecipe findTop1DbRecipeLike() {
//        return dbRecipeRepository.Top1DbRecipeLike();
//    }
//
//    /**
//     * 조회수 탑 1
//     */
//    public DbRecipe findTop1DbRecipeViewCount() {
//        return dbRecipeRepository.Top1DbRecipeViewCount();
//    }
//
//    /**
//     * 평점 탑 1
//     */
//    public DbRecipe findTop1DbRecipeRatingScore() {
//        return dbRecipeRepository.Top1DbRecipeRatingScore();
//    }

}