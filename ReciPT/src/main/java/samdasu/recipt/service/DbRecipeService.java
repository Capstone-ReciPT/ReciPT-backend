package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Db.DbDto;
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
    private final DbResponseDto dbResponseDto;

    /**
     * 평점 추가: 평점 & 평점 준 사람 더하기
     */
    @Transactional
    public void dbUpdateRatingScore(DbDto dbDto, DbUpdateRatingScoreDto dbUpdateRatingScoreDto) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbDto.getDbRecipeId())
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
        Double avgDbRatingScore = dbResponseDto.calcDbRatingScore(findDbRecipe);
        dbResponseDto.setDbRatingResult(avgDbRatingScore);
        return dbResponseDto;
    }


    /**
     * DB 레시피 저장 + (평점)
     */
    @Transactional
    public Long dbSave(DbDto dbDto) {
        DbRecipe dbRecipe = DbRecipe.createDbRecipe(dbDto.getDbFoodName(), dbDto.getDbIngredient(), dbDto.getHowToCook(), dbDto.getThumbnailImage(), dbDto.getDbContext(), dbDto.getDbImage(), dbDto.getDbLikeCount(), dbDto.getDbViewCount(), dbDto.getDbRatingScore(), dbDto.getDbRatingPeople(), dbDto.getAllergy());
        return dbRecipeRepository.save(dbRecipe).getDbRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<DbRecipe> findTop10ViewCount(DbRecipe dbRecipe) {
        return dbRecipeRepository.Top10DbRecipeView(dbRecipe);
    }

    /**
     * 좋아요 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<DbRecipe> findTop10LikeCount(DbRecipe dbRecipe) {
        return dbRecipeRepository.Top10DbRecipeLike(dbRecipe);
    }

    @Transactional(readOnly = true)
    public DbRecipe findById(Long dbRecipeId) {
        return dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));

    }

    @Transactional(readOnly = true)
    public DbRecipe findByFoodName(String dbFoodName) {
        return dbRecipeRepository.findByDbFoodName(dbFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
    }

    @Transactional(readOnly = true)
    public List<DbRecipe> findAll() {
        return dbRecipeRepository.findAll();
    }
}