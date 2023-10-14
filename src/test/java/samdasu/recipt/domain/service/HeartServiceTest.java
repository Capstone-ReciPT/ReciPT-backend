package samdasu.recipt.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.domain.controller.dto.Heart.ReviewHeartDto;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.entity.*;
import samdasu.recipt.domain.entity.enums.Authority;
import samdasu.recipt.domain.exception.ResourceNotFoundException;
import samdasu.recipt.domain.repository.HeartRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class HeartServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    HeartRepository heartRepository;
    @Autowired
    HeartService heartService;

    @Autowired
    RegisterRecipeService registerRecipeService;

    @Autowired
    RecipeService recipeService;

    @Test
    public void Recipe_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = recipeService.findByFoodName("새우두부계란찜");

        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(user.getUserId(), recipe.getRecipeId(), recipe.getFoodName(), recipe.getCategory(), recipe.getIngredient());

        //when
        heartService.insertRecipeHeart(recipeHeartDto);


        //then
        assertThat(user.getHearts().size()).isEqualTo(1);
    }

    @Test
    public void Recipe_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = recipeService.findByFoodName("새우두부계란찜");

        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(user.getUserId(), recipe.getRecipeId(), recipe.getFoodName(), recipe.getCategory(), recipe.getIngredient());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            heartService.deleteRecipeHeart(recipeHeartDto);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Could not found heart");
    }

    @Test
    public void RegisterRecipe_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        Long savedByTyping = registerRecipeSavedByTyping();
        RegisterRecipe registerRecipe = registerRecipeService.findById(savedByTyping);

        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId(), registerRecipe.getFoodName(), registerRecipe.getCategory(), registerRecipe.getIngredient());

        //when
        heartService.insertRegisterRecipeHeart(registerHeartDto);

        //then
        assertThat(user.getHearts().size()).isEqualTo(1);
    }

    @Test
    public void RegisterRecipe_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        Long savedByTyping = registerRecipeSavedByTyping();
        RegisterRecipe registerRecipe = registerRecipeService.findById(savedByTyping);

        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId(), registerRecipe.getFoodName(), registerRecipe.getCategory(), registerRecipe.getIngredient());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            heartService.deleteRegisterRecipeHeart(registerHeartDto);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Could not found heart");
    }

    @Test
    public void Review_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = recipeService.findByFoodName("새우두부계란찜");

        Review recipeReview = createRecipeReview(user, recipe);

        ReviewHeartDto recipeHeartDto = ReviewHeartDto.createRecipeHeartDto(user.getUserId(), recipeReview.getReviewId(), recipeReview.getComment());

        //when
        heartService.insertReviewHeart(recipeHeartDto);

        //then
        assertThat(user.getHearts().size()).isEqualTo(1);
    }

    @Test
    public void Review_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        Long savedByTyping = registerRecipeSavedByTyping();
        RegisterRecipe registerRecipe = registerRecipeService.findById(savedByTyping);

        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId(), registerRecipe.getFoodName(), registerRecipe.getCategory(), registerRecipe.getIngredient());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            heartService.deleteRegisterRecipeHeart(registerHeartDto);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Could not found heart");
    }

    private Long registerRecipeSavedByTyping() throws IOException {
        User user1 = User.createUser("tester1", "testId123", "test123", 10, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(user1);

        User user = user1;

        RegisterRequestDto registerRequestDto = RegisterRequestDto.createRegisterRequestDto("음식이름", "1줄평", "카테고리", null, null);

        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        MultipartFile[] uploadFiles = new MultipartFile[3];
        uploadFiles[0] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스.png"));
        uploadFiles[1] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/배민.JPG"));
        uploadFiles[2] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/vue.JPG"));

        Long saveRegisterRecipeId = registerRecipeService.registerRecipeSaveByTyping(user.getUserId(), multipartFile,uploadFiles, "콩나물, 대파, 계란", "레시피 내용", registerRequestDto);
        return saveRegisterRecipeId;
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId123", "test123", 10, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(user);

        return user;
    }

    private Review createRecipeReview(User user, Recipe recipe) {
        Review review = Review.createRecipeReview("새우두부계란찜 후기", 0, 3.0, user, recipe);
        em.persist(review);

        return review;
    }
}