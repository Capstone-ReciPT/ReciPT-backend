package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.DbDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.DbRecipeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DbRecipeService {
    private final DbRecipeRepository dbRecipeRepository;

    /**
     * 평점 계산
     */

    /**
     * 평점 순 조회 탑 10 조회
     */
    public void findTop10RatingScore() {
        List<DbRecipe> top10RatingScore = dbRecipeRepository.findTop10RatingScoreBy();
    }

    public DbDto findById(Long dbRecipeId) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        return new DbDto(dbRecipe);
    }

    public DbDto findByFoodName(String dbFoodName) {
        DbRecipe dbRecipe = dbRecipeRepository.findByDbFoodName(dbFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        return new DbDto(dbRecipe);
    }

    public List<DbRecipe> findAll() {
        return dbRecipeRepository.findAll();
    }

}
