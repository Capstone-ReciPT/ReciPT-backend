package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.Gpt;
import samdasu.recipt.repository.GptRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GptService {
    private final GptRepository gptRepository;

    @Transactional
    public Long createGptRecipe(String foodName, String ingredient, String context) {
        Gpt gpt = Gpt.createGpt(foodName, ingredient, context);
        gptRepository.save(gpt);
        return gpt.getGptId();
    }

    public Gpt getGptRecipeByGptId(Long gptId) {
        return gptRepository.findById(gptId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gpt Id:" + gptId));
    }

    public Gpt getGptRecipeByGptFoodName(String foodName) {
        return gptRepository.findByFoodName(foodName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid foodName:" + foodName));
    }

    @Transactional
    public void deleteGptRecipeByGptId(Long gptId) {
        Gpt gpt = gptRepository.findById(gptId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gpt Id:" + gptId));
        gptRepository.delete(gpt);
    }

    @Transactional
    public void deleteGptRecipeByGptFoodName(String foodName) {
        Gpt gpt = gptRepository.findByFoodName(foodName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid foodName:" + foodName));
        gptRepository.delete(gpt);
    }
}


