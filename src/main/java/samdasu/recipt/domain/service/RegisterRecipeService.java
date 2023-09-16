package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.entity.*;
import samdasu.recipt.domain.exception.DuplicateContextException;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.GptRepository;
import samdasu.recipt.domain.repository.ImageFileRepository;
import samdasu.recipt.domain.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.domain.repository.RegisterRecipeThumbnailRepository;
import samdasu.recipt.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterRecipeService {
    private final RegisterRecipeRepository registerRecipeRepository;
    private final UserRepository userRepository;
    private final ImageFileRepository imageFileRepository;
    private final RegisterRecipeThumbnailRepository thumbnailRepository;
    private final GptRepository gptRepository;

    /**
     * 평점 평균 계산
     */
    @Transactional
    public RegisterResponseDto updateRatingScore(Long registerRecipeId, ReviewRequestDto reviewRequestDto) {
        RegisterRecipe registerRecipe = findById(registerRecipeId);
        registerRecipe.updateRating(reviewRequestDto.getInputRatingScore());

        registerRecipe.calcRatingScore(registerRecipe);

        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipe);

        return registerResponseDto;
    }

    /**
     * 레시피 등록
     */
    @Transactional
    public Long registerRecipeSave(Long userId, Long imageId, Long gptId, Long thumbnailId, RegisterRequestDto requestDto) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        ImageFile imageFile = imageFileRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
        Gpt gpt = gptRepository.findById(gptId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Gpt Info"));
        RegisterRecipeThumbnail thumbnail = thumbnailRepository.findById(thumbnailId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Thumbnail Info"));

        List<RegisterRecipe> registerRecipes = registerRecipeRepository.findAll();

        RegisterRecipe createRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), thumbnail, requestDto.getTitle(), requestDto.getComment(), requestDto.getCategory(),
                gpt.getIngredient(), gpt.getContext(), 0L, 0, 0.0, 0, user, gpt, imageFile);

        for (RegisterRecipe recipe : registerRecipes) {
            if (recipe.getFoodName().equals(gpt.getFoodName()) && recipe.getTitle().equals(requestDto.getTitle())) {
                throw new DuplicateContextException("이미 저장된 레시피가 있습니다!");
            }
        }
        return registerRecipeRepository.save(createRecipe).getRegisterId();
    }

    /**
     * 등록한 레시피 삭제
     */
    @Transactional
    public void deleteRegisterRecipe(Long registerRecipeId) {
        RegisterRecipe registerRecipe = registerRecipeRepository.findById(registerRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No RegisterRecipe Info"));
        registerRecipeRepository.delete(registerRecipe);
    }


    @Transactional
    public void resetViewCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime threeSecondsAgo = LocalDateTime.now().minusSeconds(3);

//        registerRecipeRepository.resetViewCount(threeSecondsAgo);
        registerRecipeRepository.resetViewCount(yesterday);
    }

    public RegisterRecipe findByFoodName(String foodName) {
        return registerRecipeRepository.findByFoodName(foodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No RegisterRecipe Info"));
    }

    public List<RegisterRecipe> findByCategory(String category) {
        return registerRecipeRepository.findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No Recipe Info For Category"));
    }

    public List<RegisterRecipe> searchDynamicSearching(String searchingFoodName, Integer likeCond, Long viewCond) {
        return registerRecipeRepository.dynamicSearching(searchingFoodName, likeCond, viewCond);
    }

    @Transactional
    public void increaseViewCount(Long registerRecipeId) {
        RegisterRecipe registerRecipe = findById(registerRecipeId);
        registerRecipeRepository.addRegisterRecipeViewCount(registerRecipe);
    }

    public List<RegisterRecipe> findRegisterRecipes() {
        return registerRecipeRepository.findAll();
    }

    public RegisterRecipe findById(Long registerRecipeId) {
        return registerRecipeRepository.findById(registerRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No RegisterRecipe Info"));
    }

    public List<RegisterRecipe> findTop10RecentRegister() {
        return registerRecipeRepository.Top10RecentRegister();
    }

    public List<RegisterRecipe> findTop10Like() {
        return registerRecipeRepository.Top10Like();
    }

    public List<RegisterRecipe> findTop10View() {
        return registerRecipeRepository.Top10View();
    }

    public List<RegisterRecipe> findTop10RatingScore() {
        return registerRecipeRepository.Top10RatingScore();
    }

    public List<String> RecommendByAge(int userAge) {
        return registerRecipeRepository.RecommendByAge(userAge);
    }
}
