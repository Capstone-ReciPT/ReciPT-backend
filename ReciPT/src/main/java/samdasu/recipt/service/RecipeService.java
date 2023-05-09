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

    public List<Recipe> searchDynamicSearching(int likeCond, int viewCond, String searchingFoodName) {
        return recipeRepository.dynamicSearching(likeCond, viewCond, searchingFoodName);
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

    /**
     * 좋아요 탑 10 조회
     */
    public List<Recipe> findTop10RecipeLike() {
        return recipeRepository.Top10RecipeLike();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<Recipe> findTop10RecipeView() {
        return recipeRepository.Top10RecipeView();
    }

    /**
     * 평점 순 탑 10 조회
     */
    public List<Recipe> findTop10RecipeRatingScore() {
        return recipeRepository.Top10RecipeRatingScore();
    }
}