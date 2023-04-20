package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.GptDto;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.GptRecipeRepository;

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
    public Long gptSave(Long gptRecipeId) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No gptRecipe Info"));
        GptRecipe gptRecipeSave = gptRecipeRepository.save(gptRecipe);
        return gptRecipeSave.getGptRecipeId();
    }

    /**
     * 평점 순 탑 10 조회
     */
    public void findTop10RatingScore() {
        List<GptRecipe> top10ViewCount = gptRecipeRepository.findTop10ViewCountBy();
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
