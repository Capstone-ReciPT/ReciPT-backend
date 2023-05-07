package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Gpt;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.entity.RegisterRecipe;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.GptRepository;
import samdasu.recipt.repository.ImageFileRepository;
import samdasu.recipt.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterRecipeService {
    private final RegisterRecipeRepository registerRecipeRepository;
    private final UserRepository userRepository;
    private final ImageFileRepository imageFileRepository;
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
    public Long registerRecipeSave(Long userId, Long imageId, Long gptId, RegisterRequestDto requestDto) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        ImageFile imageFile = imageFileRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
        Gpt gpt = gptRepository.findById(gptId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Gpt Info"));

        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), requestDto.getThumbnailImage(), requestDto.getTitle(), requestDto.getComment(), requestDto.getCategory(),
                gpt.getIngredient(), gpt.getContext(), 0L, 0, 0.0, 0, user, gpt, imageFile);

        return registerRecipeRepository.save(registerRecipe).getRegisterId();
    }


    public RegisterRecipe findByFoodName(String foodName) {
        return registerRecipeRepository.findByFoodName(foodName)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No RegisterRecipe Info"));
    }

    /**
     * 음식명 포함 조회(like '%foodName%')
     */
    public List<RegisterRecipe> findRegisterRecipeByContain(String searchingFoodName) {
        return registerRecipeRepository.findRegisterRecipeByContain(searchingFoodName);
    }

    /**
     * 사용자 입력 값 보다 높은 좋아요 찾기
     */
    public List<RegisterRecipe> findSearchingRegisterRecipeLikeByInputNum(int inputNum) {
        return registerRecipeRepository.SearchingRegisterRecipeLikeByInputNum(inputNum);
    }

    /**
     * 사용자 입력 값 보다 높은 조회수 찾기
     */
    public List<RegisterRecipe> findSearchingRegisterRecipeViewCountByInputNum(int inputNum) {
        return registerRecipeRepository.SearchingRegisterRecipeViewCountByInputNum(inputNum);
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
//    /**
//     * 조회 수 탑 10 조회
//     */
//    public List<GptRecipe> findTop10ViewCount() {
//        return gpt.Top10GptRecipeView();
//    }
//
//    /**
//     * 좋아요 탑 10 조회
//     */
//    public List<GptRecipe> findTop10LikeCount() {
//        return gpt.Top10GptRecipeLike();
//    }
//
//    /**
//     * 좋아요 탑 1
//     */
//    public GptRecipe findTop1GptRecipeLike() {
//        return gpt.Top1GptRecipeLike();
//    }
//
//    /**
//     * 조회수 탑 1
//     */
//    public GptRecipe findTop1GptRecipeViewCount() {
//        return gpt.Top1GptRecipeViewCount();
//    }
//
//    /**
//     * 평점 탑 1
//     */
//    public GptRecipe findTop1GptRecipeRatingScore() {
//        return gpt.Top1GptRecipeRatingScore();
//    }
}
