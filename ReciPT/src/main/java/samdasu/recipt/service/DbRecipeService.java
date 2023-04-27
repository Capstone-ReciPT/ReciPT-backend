package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Db.DbRequestDto;
import samdasu.recipt.controller.dto.Db.DbResponseDto;
import samdasu.recipt.controller.dto.Db.DbUpdateRatingScoreDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DbRecipeService {
    private final DbRecipeRepository dbRecipeRepository;

    /**
     * 평점 추가: 평점 & 평점 준 사람 더하기
     */
    @Transactional
    public void dbUpdateRatingScore(Long dbRecipeId, DbUpdateRatingScoreDto dbUpdateRatingScoreDto) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        DbUpdateRatingScoreDto dbUpdateRatingScore = dbUpdateRatingScoreDto.createDbUpdateRatingScoreDto(dbUpdateRatingScoreDto.getDbRatingScore(), dbUpdateRatingScoreDto.getDbRatingPeople());
        dbRecipe.updateRating(dbUpdateRatingScore);
    }

    /**
     * 평점 평균 계산
     */
    @Transactional
    public DbResponseDto calcDbRatingScore(DbRecipe dbRecipe) {
        DbRecipe findDbRecipe = dbRecipeRepository.findById(dbRecipe.getDbRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        Double avgDbRatingScore = dbRecipe.calcDbRatingScore(findDbRecipe);

        DbResponseDto dbResponseDto = DbResponseDto.createDbResponseDto(findDbRecipe);
        dbResponseDto.setDbRatingResult(avgDbRatingScore);
        return dbResponseDto;
    }


    /**
     * DB 레시피 저장 + (평점)
     */
    @Transactional
    public Long dbSave(DbRequestDto dbRequestDto) {
        DbRecipe dbRecipe = DbRecipe.createDbRecipe(dbRequestDto.getDbFoodName(), dbRequestDto.getDbIngredient(), dbRequestDto.getHowToCook(), dbRequestDto.getThumbnailImage(), dbRequestDto.getDbContext(), dbRequestDto.getDbImage(), 0, 0, 0.0, 0, dbRequestDto.getAllergy());
        return dbRecipeRepository.save(dbRecipe).getDbRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<DbRecipe> findTop10ViewCount() {
        return dbRecipeRepository.Top10DbRecipeView();
    }

    /**
     * 음식명 포함 조회
     */
    List<DbRecipe> AllDbRecipeView(DbRecipe dbRecipe, String inputFoodName) {
        return dbRecipeRepository.DbRecipeByFoodNameView(dbRecipe, inputFoodName);
    }

    /**
     * 좋아요 탑 10 조회
     */
    public List<DbRecipe> findTop10LikeCount() {
        return dbRecipeRepository.Top10DbRecipeLike();
    }

    public DbRecipe findByFoodName(String dbFoodName) {
        return dbRecipeRepository.findByDbFoodName(dbFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
    }

    public List<DbRecipe> findAll() {
        return dbRecipeRepository.findAll();
    }
}