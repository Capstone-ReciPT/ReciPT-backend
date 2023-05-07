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
        return recipeRepository.searchingRecipeLikeByInputNum(inputNum);
    }

    /**
     * 사용자 입력 값 보다 높은 조회수 찾기
     */
    public List<Recipe> findSearchingRecipeViewCountByInputNum(int inputNum) {
        return recipeRepository.searchingRecipeViewCountByInputNum(inputNum);
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

}