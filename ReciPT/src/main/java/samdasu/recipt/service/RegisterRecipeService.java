package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.*;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.GptRepository;
import samdasu.recipt.repository.ImageFileRepository;
import samdasu.recipt.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.repository.RegisterRecipeThumbnailRepository;
import samdasu.recipt.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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

        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), thumbnail, requestDto.getTitle(), requestDto.getComment(), requestDto.getCategory(),
                gpt.getIngredient(), gpt.getContext(), 0L, 0, 0.0, 0, user, gpt, imageFile);

        return registerRecipeRepository.save(registerRecipe).getRegisterId();
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

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void resetViewCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        registerRecipeRepository.resetViewCount(yesterday);
    }

    public RegisterRecipe findByFoodName(String foodName) {
        return registerRecipeRepository.findByFoodName(foodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No RegisterRecipe Info"));
    }

    public List<RegisterRecipe> searchDynamicSearching(int likeCond, int viewCond, String searchingFoodName) {
        return registerRecipeRepository.dynamicSearching(likeCond, viewCond, searchingFoodName);
    }

    @Transactional
    public void IncreaseViewCount(Long registerRecipeId) {
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
