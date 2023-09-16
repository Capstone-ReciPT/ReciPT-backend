package samdasu.recipt.domain.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //UserService
    @Pointcut("execution(public Long samdasu.recipt.domain.service.UserService.join(*, *))")
    public void login() {
    }

    @Pointcut("execution(public Long samdasu.recipt.domain.service.UserService.update(*, *))")
    public void userUpdate() {
    }

    //ReviewService
    @Pointcut("execution(public Long samdasu.recipt.domain.service.ReviewService.saveRecipeReview(*, *, *))")
    public void saveRecipeReview() {
    }

    @Pointcut("execution(public Long samdasu.recipt.domain.service.ReviewService.saveRegisterRecipeReview(*, *, *))")
    public void saveRegisterRecipeReview() {
    }
    @Pointcut("execution(public Long samdasu.recipt.domain.service.ReviewService.update(*, *))")
    public void reviewUpdate() {
    }

    @Pointcut("execution(public Long samdasu.recipt.domain.service.ReviewService.delete(*))")
    public void reviewDelete() {
    }

    //RegisterRecipeService
    @Pointcut("execution(* samdasu.recipt.domain.service.RegisterRecipeService.updateRatingScore(*, *))")
    public void RegisterRecipeUpdateRatingScore() {
    }
    @Pointcut("execution(public Long samdasu.recipt.domain.service.RegisterRecipeService.registerRecipeSave(*, *, *, *, *))")
    public void registerRecipeSave() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.RegisterRecipeService.deleteRegisterRecipe(*))")
    public void deleteRegisterRecipe() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.RegisterRecipeService.resetViewCount())")
    public void RegisterRecipeResetViewCount() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.RegisterRecipeService.increaseViewCount(*))")
    public void RegisterRecipeIncreaseViewCount() {
    }

    //RecipeService
    @Pointcut("execution(* samdasu.recipt.domain.service.RecipeService.updateRatingScore(*, *))")
    public void recipeUpdateRatingScore() {
    }


    @Pointcut("execution(public void samdasu.recipt.domain.service.RecipeService.resetViewCount())")
    public void recipeResetViewCount() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.RecipeService.increaseViewCount(*))")
    public void recipeIncreaseViewCount() {
    }


    //GptService
    @Pointcut("execution(public Long samdasu.recipt.domain.service.GptService.createGptRecipe(*, *, *, *))")
    public void createGptRecipe() {
    }

    //HeartService
    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.insertRecipeHeart(*))")
    public void insertRecipeHeart() {
    }


    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.deleteRecipeHeart(*))")
    public void deleteRecipeHeart() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.insertRegisterRecipeHeart(*))")
    public void insertRegisterRecipeHeart() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.deleteRegisterRecipeHeart(*))")
    public void deleteRegisterRecipeHeart() {
    }


    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.insertReviewHeart(*))")
    public void insertReviewHeart() {
    }

    @Pointcut("execution(public void samdasu.recipt.domain.service.HeartService.deleteReviewHeart(*))")
    public void deleteReviewHeart() {
    }
}
