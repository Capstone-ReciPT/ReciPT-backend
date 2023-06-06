package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.ReviewHeartDto;
import samdasu.recipt.domain.entity.*;
import samdasu.recipt.domain.exception.DuplicateResourceException;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.HeartRepository;
import samdasu.recipt.domain.repository.Recipe.RecipeRepository;
import samdasu.recipt.domain.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.domain.repository.Review.ReviewRepository;
import samdasu.recipt.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RegisterRecipeRepository registerRecipeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void insertRecipeHeart(RecipeHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        Recipe recipe = findRecipeById(heartDto);

        //이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByUserAndRecipe(user, recipe).isPresent()) {
            //TODO 409에러로 변경
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "Recipe id : " + recipe.getRecipeId());
        }

        Heart heart = Heart.createRecipeHeart(user, recipe);
        heartRepository.save(heart);
        recipeRepository.addRecipeLikeCount(recipe);
    }


    @Transactional
    public void deleteRecipeHeart(RecipeHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        Recipe recipe = findRecipeById(heartDto);

        Heart heart = heartRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        recipeRepository.subRecipeLikeCount(recipe);
    }

    public Boolean checkRecipeHeart(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Recipe Info"));

        if (heartRepository.findByUserAndRecipe(user, recipe).isPresent()) {
            return true; //좋아요 누른 상태
        } else {
            return false; //좋아요 누르지 않은 상태
        }
    }

    @Transactional
    public void insertRegisterRecipeHeart(RegisterHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        RegisterRecipe registerRecipe = findRegisterRecipeById(heartDto);

        if (heartRepository.findByUserAndRegisterRecipe(user, registerRecipe).isPresent()) {
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "RegisterRecipe id : " + registerRecipe.getRegisterId());
        }

        Heart heart = Heart.createRegiterRecipeHeart(user, registerRecipe);
        heartRepository.save(heart);
        registerRecipeRepository.addRegisterRecipeLikeCount(registerRecipe);
    }

    @Transactional
    public void deleteRegisterRecipeHeart(RegisterHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        RegisterRecipe registerRecipe = findRegisterRecipeById(heartDto);

        Heart heart = heartRepository.findByUserAndRegisterRecipe(user, registerRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        registerRecipeRepository.subRegisterRecipeLikeCount(registerRecipe);
    }

    public Boolean checkRegisterRecipeHeart(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        RegisterRecipe recipe = registerRecipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No RegisterRecipe Info"));

        if (heartRepository.findByUserAndRegisterRecipe(user, recipe).isPresent()) {
            return true; //좋아요 누른 상태
        } else {
            return false; //좋아요 누르지 않은 상태
        }
    }

    @Transactional
    public void insertReviewHeart(ReviewHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        Review review = findReviewById(heartDto);

        if (heartRepository.findByUserAndReview(user, review).isPresent()) {
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "Review id : " + review.getReviewId());
        }

        Heart heart = Heart.createReviewHeart(user, review);
        heartRepository.save(heart);
        reviewRepository.addReviewLikeCount(review);
    }

    @Transactional
    public void deleteReviewHeart(ReviewHeartDto heartDto) {
        User user = findUserById(heartDto.getUserId());
        Review review = findReviewById(heartDto);

        Heart heart = heartRepository.findByUserAndReview(user, review)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found heart"));

        heartRepository.delete(heart);
        reviewRepository.subReviewLikeCount(review);
    }

    private User findUserById(Long heartDto) {
        User user = userRepository.findById(heartDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return user;
    }

    private Recipe findRecipeById(RecipeHeartDto recipeHeartDto) {
        return recipeRepository.findById(recipeHeartDto.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Recipe Info"));
    }

    private RegisterRecipe findRegisterRecipeById(RegisterHeartDto registerHeartDto) {
        return registerRecipeRepository.findById(registerHeartDto.getRegisterId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No RegisterRecipe Info"));
    }

    private Review findReviewById(ReviewHeartDto reviewHeartDto) {
        return reviewRepository.findById(reviewHeartDto.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
    }
}
