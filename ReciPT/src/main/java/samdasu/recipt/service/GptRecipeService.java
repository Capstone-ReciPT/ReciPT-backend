package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Gpt.GptRequestDto;
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

    /**
     * 평점 추가: 평점 & 평점 준 사람 더하기
     */
    @Transactional
    public void gptUpdateRatingScore(Long gptRecipeId, GptUpdateRatingScoreDto gptUpdateRatingScoreDto) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptRecipeId)
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
        Double avgDbRatingScore = gptRecipe.calcGptRatingScore(findGptRecipe);

        GptResponseDto gptResponseDto = GptResponseDto.createGptResponseDto(findGptRecipe);
        gptResponseDto.setDbRatingResult(avgDbRatingScore);
        return gptResponseDto;
    }

    /**
     * gpt 레시피 저장
     * - gpt Api 응답을 gptDto에 담고 저장하기
     */
    @Transactional
    public Long gptSave(GptRequestDto gptRequestDto) {
        GptRecipe gptRecipe = GptRecipe.createGptRecipe(gptRequestDto.getGptFoodName(), gptRequestDto.getGptIngredient(), gptRequestDto.getGptHowToCook(), gptRequestDto.getGptTip(), 0, 0, 0.0, 0, gptRequestDto.getAllergy());
        return gptRecipeRepository.save(gptRecipe).getGptRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<GptRecipe> findTop10ViewCount() {
        return gptRecipeRepository.Top10GptRecipeView();
    }

    /**
     * 좋아요 탑 10 조회
     */
    public List<GptRecipe> findTop10LikeCount() {
        return gptRecipeRepository.Top10GptRecipeLike();
    }

    public GptRequestDto findByFoodName(String gptFoodName) {
        GptRecipe gptRecipe = gptRecipeRepository.findByGptFoodName(gptFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        return new GptRequestDto(gptRecipe);
    }

    public List<GptRecipe> findAll() {
        return gptRecipeRepository.findAll();
    }
}
