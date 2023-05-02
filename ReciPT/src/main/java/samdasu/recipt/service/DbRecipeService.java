package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Db.DbRequestDto;
import samdasu.recipt.controller.dto.Db.DbResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
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
    public void dbUpdateRatingScore(Long dbRecipeId, ReviewRequestDto reviewRequestDto) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
        dbRecipe.updateRating(reviewRequestDto.getRatingScore());
    }

    /**
     * 평점 평균 계산
     */
    @Transactional
    public DbResponseDto calcDbRatingScore(DbRecipe dbRecipe) {
        Double avgDbRatingScore = dbRecipe.calcDbRatingScore(dbRecipe);

        DbResponseDto dbResponseDto = DbResponseDto.createDbResponseDto(dbRecipe, dbRecipe.getDbRecipeId());
        dbResponseDto.setDbRatingResult(avgDbRatingScore);
        return dbResponseDto;
    }


    /**
     * DB 레시피 저장 + (평점)
     */
    @Transactional
    public Long dbSave(DbRequestDto dbRequestDto) {
        DbRecipe dbRecipe = DbRecipe.createDbRecipe(dbRequestDto.getDbFoodName(), dbRequestDto.getDbIngredient(), dbRequestDto.getHowToCook(), dbRequestDto.getThumbnailImage(), dbRequestDto.getDbContext(), dbRequestDto.getDbImage(), 0, 0L, dbRequestDto.getDbRatingScore(), 1, dbRequestDto.getAllergy());
        return dbRecipeRepository.save(dbRecipe).getDbRecipeId();
    }

    public DbRecipe findByFoodName(String dbFoodName) {
        return dbRecipeRepository.findByDbFoodName(dbFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No dbRecipe Info"));
    }

    /**
     * 음식명 포함 조회(like '%foodName%')
     */
    public List<DbRecipe> findDbRecipeByContain(String searchingFoodName) {
        return dbRecipeRepository.findDbRecipeByContain(searchingFoodName);
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<DbRecipe> findTop10ViewCount() {
        return dbRecipeRepository.Top10DbRecipeView();
    }

    /**
     * 좋아요 탑 10 조회
     */
    public List<DbRecipe> findTop10LikeCount() {
        return dbRecipeRepository.Top10DbRecipeLike();
    }

    /**
     * 좋아요 탑 1
     */
    public DbRecipe findTop1DbRecipeLike() {
        return dbRecipeRepository.Top1DbRecipeLike();
    }

    /**
     * 조회수 탑 1
     */
    public DbRecipe findTop1DbRecipeViewCount() {
        return dbRecipeRepository.Top1DbRecipeViewCount();
    }

    /**
     * 평점 탑 1
     */
    public DbRecipe findTop1DbRecipeRatingScore() {
        return dbRecipeRepository.Top1DbRecipeRatingScore();
    }

    /**
     * 사용자 입력 값 보다 높은 좋아요 찾기
     */
    public List<DbRecipe> findSearchingDbRecipeLikeByInputNum(int inputNum) {
        return dbRecipeRepository.SearchingDbRecipeLikeByInputNum(inputNum);
    }

    /**
     * 사용자 입력 값 보다 높은 조회수 찾기
     */
    public List<DbRecipe> findSearchingDbRecipeViewCountByInputNum(int inputNum) {
        return dbRecipeRepository.SearchingDbRecipeViewCountByInputNum(inputNum);
    }

    public List<DbRecipe> findDbRecipes() {
        return dbRecipeRepository.findAll();
    }

    public DbRecipe findOne(Long dbRecipeId) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
        return dbRecipe;
    }

    @Transactional
    public void IncreaseViewCount(Long dbRecipeId) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        dbRecipeRepository.addDbViewCount(dbRecipe);
    }
}