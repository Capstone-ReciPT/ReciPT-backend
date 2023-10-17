package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.entity.Gpt;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.Gpt.GptRepository;
import samdasu.recipt.domain.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GptService {
    private final GptRepository gptRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long createGptRecipe(String foodName, String ingredient, String context, Long userId) {
        User user = findUserById(userId);
        Gpt gpt = Gpt.createGpt(foodName, ingredient, context, user);
        gptRepository.save(gpt);
        return gpt.getGptId();
    }

    public List<String> getGptRecipesByFoodNameAndUserId(String foodName, Long userId) {
        User user = findUserById(userId);
        List<String> findGptRecipes = gptRepository.findByFoodNameAndUer(foodName, user);
        if (findGptRecipes.isEmpty()) {
            throw new ResourceNotFoundException("Fail: No Gpt Recipe info");
        }
        return findGptRecipes;
    }
    public Gpt getGptRecipeByUserIdAndGptId(User user, Long gptId) {
        return gptRepository.findByUserAndGptId(user, gptId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Gpt Recipe info"));
    }

    public List<Gpt> getGptRecipesByUserId(User user) {
        return gptRepository.findByUser(user);
    }

    public Gpt getGptRecipeByGptId(Long gptId) {
        return gptRepository.findById(gptId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gpt Id:" + gptId));
    }

    public Gpt getGptRecipeByFoodName(String foodName) {
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

    private User findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return user;
    }
}


