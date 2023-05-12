package samdasu.recipt.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterRecipeServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    RegisterRecipeService registerRecipeService;


    @Test
    public void 평점_갱신() throws Exception {
        //given
        Profile profile = createProfile();
        User user = createUser(profile);
        Gpt gpt = createGpt();
        RegisterRecipeThumbnail thumbnail = createThumbnail();
        ImageFile imageFile = createImageFile();
        RegisterRecipe recipe = createRecipe(user, gpt, thumbnail, imageFile);
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("만두 맛있다.", 4.0);

        //when
        registerRecipeService.updateRatingScore(recipe.getRegisterId(), reviewRequestDto);

        //then
        assertThat(recipe.getRatingPeople()).isEqualTo(2);
        assertThat(recipe.getRatingScore()).isEqualTo(4.5);
    }

    @Test
    public void 레시피_저장() throws Exception {
        //given
        Profile profile = createProfile();
        User user = createUser(profile);
        Gpt gpt = createGpt();
        ImageFile imageFile = createImageFile();

        //when
        RegisterRequestDto registerRequestDto = RegisterRequestDto.createRegisterRequestDto(null, "제목", "1줄평", "카테고리");
        RegisterRecipeThumbnail thumbnail = createThumbnail();
        Long saveRegisterRecipeId = registerRecipeService.registerRecipeSave(user.getUserId(), imageFile.getImageId(), gpt.getGptId(), thumbnail.getThumbnailId(), registerRequestDto);

        RegisterRecipe findById = registerRecipeService.findById(saveRegisterRecipeId);

        //then
        assertThat(findById.getTitle()).isEqualTo("제목");
    }

    @Test
    public void 레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        RegisterRecipe registerRecipe = registerRecipeService.findByFoodName("만두");

        //then
        assertThat(registerRecipe.getFoodName()).isEqualTo("만두");
    }

    @Test
    public void 검색조건_동적쿼리() throws Exception {
        //given

        //when
        List<RegisterRecipe> findRegisterRecipes = registerRecipeService.searchDynamicSearching(0, 0, "만두");

        //then
        assertThat(findRegisterRecipes.size()).isEqualTo(1);
    }


    @Test
    public void 레시피_전체조회() throws Exception {
        //given

        //when
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();

        //then
        assertThat(registerRecipes.size()).isEqualTo(2);
    }

    @Test
    public void 레시피_좋아요_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeLike = registerRecipeService.findTop10Like();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeLike) {
            log.info("registerRecipe.getLikeCount() = " + registerRecipe.getLikeCount());
        }
    }

    @Test
    public void 레시피_조회수_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeView = registerRecipeService.findTop10View();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeView) {
            log.info("registerRecipe.getLikeCount() = " + registerRecipe.getLikeCount());
        }
    }


    @Test
    public void 레시피_평점순_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeRatingScore = registerRecipeService.findTop10RatingScore();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeRatingScore) {
            log.info("registerRecipe.getLikeCount() = " + registerRecipe.getLikeCount());
        }
    }


    private ImageFile createImageFile() {
        ImageFile imageFile = ImageFile.createImageFile("만두 사진1", "jpg", null);
        em.persist(imageFile);

        return imageFile;
    }

    private Gpt createGpt() {
        Gpt gpt = Gpt.createGpt("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기");
        em.persist(gpt);

        return gpt;
    }

    private User createUser(Profile profile) {
        User user = User.createUser("tester1", "testId", "test1234", 10, profile);
        em.persist(user);

        return user;
    }

    private Profile createProfile() {
        Profile profile = Profile.createProfile("프로필 사진", "jpg", null);
        em.persist(profile);
        return profile;
    }

    private RegisterRecipeThumbnail createThumbnail() {
        RegisterRecipeThumbnail thumbnail = RegisterRecipeThumbnail.createThumbnail("썸네일 사진", "png", null);
        em.persist(thumbnail);
        return thumbnail;
    }

    private RegisterRecipe createRecipe(User user, Gpt gpt, RegisterRecipeThumbnail thumbnail, ImageFile imageFile) {
        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), thumbnail, "만두 먹기", "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                0L, 0, 5.0, 1, user, gpt, imageFile);
        em.persist(registerRecipe);

        return registerRecipe;
    }
}