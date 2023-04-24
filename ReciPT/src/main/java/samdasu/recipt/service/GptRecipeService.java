package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Db.DbDto;
import samdasu.recipt.controller.dto.Gpt.GptDto;
import samdasu.recipt.controller.dto.Gpt.GptResponseDto;
import samdasu.recipt.controller.dto.Gpt.GptUpdateRatingScoreDto;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GptRecipeService {
    private final GptRecipeRepository gptRecipeRepository;

    private final GptResponseDto gptResponseDto;

    /**
     * 평점 추가: 평점 & 평점 준 사람 더하기
     */
    @Transactional
    public void gptUpdateRatingScore(DbDto dbDto, GptUpdateRatingScoreDto gptUpdateRatingScoreDto) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(dbDto.getDbRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        GptUpdateRatingScoreDto gptUpdateRatingScore = gptUpdateRatingScoreDto.createGptUpdateRatingScoreDto(gptUpdateRatingScoreDto.getGptRatingScore(), gptUpdateRatingScoreDto.getGptRatingPeople());
        gptRecipe.updateRating(gptUpdateRatingScore);
    }

    /**
     * 평점 평균 계산
     */
    @Transactional
    public GptResponseDto calcGptRatingScore(GptRecipe gptRecipe) {
        GptRecipe findGptRecipe = gptRecipeRepository.findById(gptRecipe.getGptRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        Double avgDbRatingScore = gptResponseDto.calcGptRatingScore(findGptRecipe);
        gptResponseDto.setDbRatingResult(avgDbRatingScore);
        return gptResponseDto;
    }

    /**
     * gpt 레시피 저장
     * - gpt Api 응답을 gptDto에 담고 저장하기
     */
    @Transactional
    public Long gptSave(GptDto gptDto) {
        GptRecipe gptRecipe = GptRecipe.createGptRecipe(gptDto.getGptFoodName(), gptDto.getGptIngredient(), gptDto.getGptHowToCook(), gptDto.getGptTip(), gptDto.getGptLikeCount(), gptDto.getGptViewCount(), gptDto.getGptRatingScore(), gptDto.getGptRatingPeople(), gptDto.getAllergy());
        return gptRecipeRepository.save(gptRecipe).getGptRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<GptRecipe> findTop10ViewCount(GptRecipe gptRecipe) {
        return gptRecipeRepository.Top10GptRecipeView(gptRecipe);
    }

    /**
     * 좋아요 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<GptRecipe> findTop10LikeCount(GptRecipe gptRecipe) {
        return gptRecipeRepository.Top10GptRecipeLike(gptRecipe);
    }

    @Transactional(readOnly = true)
    public GptDto findById(Long gptRecipeId) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        return new GptDto(gptRecipe);
    }

    @Transactional(readOnly = true)
    public GptDto findByFoodName(String gptFoodName) {
        GptRecipe gptRecipe = gptRecipeRepository.findByGptFoodName(gptFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        return new GptDto(gptRecipe);
    }

    @Transactional(readOnly = true)
    public List<GptRecipe> findAll() {
        return gptRecipeRepository.findAll();
    }
}
