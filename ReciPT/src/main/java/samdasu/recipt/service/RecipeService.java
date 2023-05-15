package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Recipe.RecipeResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.Recipe.RecipeRepository;

import java.time.LocalDateTime;
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

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void resetViewCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        recipeRepository.resetViewCount(yesterday);
    }

    public Recipe findByFoodName(String foodName) {
        return recipeRepository.findByFoodName(foodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No Recipe Info"));
    }

    public List<Recipe> searchDynamicSearching(String searchingFoodName, Integer likeCond, Long viewCond) {
        return recipeRepository.dynamicSearching(searchingFoodName, likeCond, viewCond);
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

    public List<Recipe> findTop10RecentRegister() {
        return recipeRepository.Top10RecentRegister();
    }

    public List<Recipe> findTop10Like() {
        return recipeRepository.Top10Like();
    }

    public List<Recipe> findTop10View() {
        return recipeRepository.Top10View();
    }

    public List<Recipe> findTop10RatingScore() {
        return recipeRepository.Top10RatingScore();
    }

    public List<String> RecommendByRandH2() {
        return recipeRepository.RecommendByRandH2();
    }

    public List<String> RecommendByRandMySql() {
        return recipeRepository.RecommendByRandMySql();
    }
}