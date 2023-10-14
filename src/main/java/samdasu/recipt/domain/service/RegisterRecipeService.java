package samdasu.recipt.domain.service;

import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.controller.dto.Review.UpdateRatingScoreRequestDto;
import samdasu.recipt.domain.entity.*;
import samdasu.recipt.domain.exception.DuplicateContextException;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.Gpt.GptRepository;
import samdasu.recipt.domain.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.domain.repository.UserRepository;
import samdasu.recipt.utils.Image.AttachImage;
import samdasu.recipt.utils.Image.UploadService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterRecipeService {
    private final RegisterRecipeRepository registerRecipeRepository;
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final GptRepository gptRepository;

    @Value("${image.register.path}")
    private String registerImage;

    /**
     * 평점 평균 계산
     */
    @Transactional
    public RegisterResponseDto updateRatingScore(Long registerRecipeId, UpdateRatingScoreRequestDto requestDto) {
        RegisterRecipe registerRecipe = findById(registerRecipeId);
        registerRecipe.updateRating(requestDto.getInputRatingScore());

        registerRecipe.calcRatingScore(registerRecipe);

        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipe);

        return registerResponseDto;
    }

//    @Transactional
//    public RegisterResponseDto updateRatingScore(Long registerRecipeId, ReviewRequestDto reviewRequestDto) {
//        RegisterRecipe registerRecipe = findById(registerRecipeId);
//        registerRecipe.updateRating(reviewRequestDto.getInputRatingScore());
//
//        registerRecipe.calcRatingScore(registerRecipe);
//
//        RegisterResponseDto registerResponseDto = RegisterResponseDto.createRegisterResponseDto(registerRecipe);
//
//        return registerResponseDto;
//    }

    /**
     * 레시피 등록
     */
    @Counted("register.recipe")
    @Transactional
    public Long registerRecipeSaveByGpt(Long userId, MultipartFile uploadFile,  MultipartFile[] uploadFiles, String foodName, RegisterRequestDto requestDto) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        AttachImage thumbnail = uploadService.uploadOne(registerImage, uploadFile, user.getUsername());
        List<AttachImage> registerImages = uploadService.uploadGtOne(registerImage, uploadFiles, user.getUsername());

        List<String> registerImagesPath = new ArrayList<>();
        for (AttachImage image : registerImages) {
            String filename = image.getSavedName();
            registerImagesPath.add(filename);
        }

        List<Gpt> gptList = user.getGpt();
        Long gptId = null;

        for (Gpt gpt : gptList) {
            if (gpt.getFoodName().equals(foodName)) {
                gptId = gpt.getGptId();
                break;
            }
        }

        Gpt gpt = gptRepository.findById(gptId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Gpt Info"));

        List<RegisterRecipe> registerRecipes = registerRecipeRepository.findAll();

        RegisterRecipe createRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(),  requestDto.getComment(), requestDto.getCategory(),
                gpt.getIngredient(), gpt.getContext(), 0L, 0, 0.0, 0,thumbnail.getSavedName(), registerImagesPath, user, gpt);

        for (RegisterRecipe recipe : registerRecipes) {
            if (recipe.getFoodName().equals(gpt.getFoodName()) && recipe.getComment().equals(requestDto.getComment())) {
                throw new DuplicateContextException("이미 저장된 레시피가 있습니다!");
            }
        }
        return registerRecipeRepository.save(createRecipe).getRegisterId();
    }

    @Counted("register.recipe")
    @Transactional
    public Long registerRecipeSaveByTyping(Long userId, MultipartFile uploadFile,  MultipartFile[] uploadFiles, String ingredients, String contexts, RegisterRequestDto requestDto) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        AttachImage thumbnail = uploadService.uploadOne(registerImage, uploadFile, user.getUsername());
        List<AttachImage> registerImages = uploadService.uploadGtOne(registerImage, uploadFiles, user.getUsername());

        List<String> registerImagesPath = new ArrayList<>();
        for (AttachImage image : registerImages) {
            String filename = image.getSavedName();
            registerImagesPath.add(filename);
        }

        List<RegisterRecipe> registerRecipes = registerRecipeRepository.findAll();

        RegisterRecipe createRecipe = RegisterRecipe.createRegisterRecipeByTying(requestDto.getFoodName(), requestDto.getComment(), requestDto.getCategory(),
                ingredients, contexts, 0L, 0, 0.0, 0,thumbnail.getSavedName(), registerImagesPath, user);

        for (RegisterRecipe recipe : registerRecipes) {
            if (recipe.getFoodName().equals(requestDto.getFoodName()) && recipe.getComment().equals(requestDto.getComment())) {
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
    public List<RegisterRecipe> increaseViewCount(Long registerRecipeId) {
        RegisterRecipe registerRecipe = findById(registerRecipeId);
        return registerRecipeRepository.addRegisterRecipeViewCount(registerRecipe);
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
