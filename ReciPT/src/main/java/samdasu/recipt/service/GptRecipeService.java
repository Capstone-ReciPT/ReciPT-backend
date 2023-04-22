package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.GptDto;
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
     * gpt 레시피 저장
     * - gpt Api 응답을 gptDto에 담고 저장하기
     */
    @Transactional
    public Long gptSave(GptDto gptDto) {
        GptRecipe gptRecipe = GptRecipe.createGptRecipe(gptDto.getGptFoodName(), gptDto.getGptIngredient(), gptDto.getGptHowToCook(), gptDto.getGptTip(), gptDto.getGptLikeCount(), gptDto.getGptViewCount(), gptDto.getAllergy());
        return gptRecipeRepository.save(gptRecipe).getGptRecipeId();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public List<GptRecipe> findTop10ViewCount(GptRecipe gptRecipe) {
        return gptRecipeRepository.Top10GptRecipeLike(gptRecipe);
    }

    public GptDto findById(Long gptRecipeId) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        return new GptDto(gptRecipe);
    }

    public GptDto findByFoodName(String gptFoodName) {
        GptRecipe gptRecipe = gptRecipeRepository.findByGptFoodName(gptFoodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        return new GptDto(gptRecipe);
    }

    public List<GptRecipe> findAll() {
        return gptRecipeRepository.findAll();
    }
}
