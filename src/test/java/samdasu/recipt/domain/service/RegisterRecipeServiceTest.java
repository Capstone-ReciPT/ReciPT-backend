package samdasu.recipt.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.entity.*;
import samdasu.recipt.domain.entity.enums.Authority;
import samdasu.recipt.domain.exception.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class RegisterRecipeServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    RegisterRecipeService registerRecipeService;


    @Test
    public void 평점_갱신() throws Exception {
        //given
        User user1 = User.createUser("tester1", "testId123", "test123", 10, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(user1);

        User user = user1;
        Gpt gpt = createGpt(user);
        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                0L, 0, 5.0, 1, null, null, user, gpt );
        em.persist(registerRecipe);

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("만두 맛있다.", 4.0);

        //when
        registerRecipeService.updateRatingScore(registerRecipe.getRegisterId(), reviewRequestDto);

        //then
        assertThat(registerRecipe.getRatingPeople()).isEqualTo(2);
        assertThat(registerRecipe.getRatingScore()).isEqualTo(4.5);
    }

    @DisplayName("GPT 버튼을 이용한 레시피 등록")
    @Test
    public void registerRecipeSaveByGpt() throws Exception {
        //given
        User user1 = User.createUser("tester1", "testId123", "test123", 10, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(user1);

        User user = user1;
        Gpt gpt = createGpt(user);

        //when
        RegisterRequestDto registerRequestDto = RegisterRequestDto.createRegisterRequestDto("제목", "1줄평", "카테고리", null, null);

        MultipartFile multipartFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스자바.jpeg"));
        MultipartFile[] uploadFiles = new MultipartFile[3];
        uploadFiles[0] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/뉴진스.png"));
        uploadFiles[1] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/배민.JPG"));
        uploadFiles[2] = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream("/Users/jaehyun/Pictures/뉴진스/vue.JPG"));

        Long saveRegisterRecipeId = registerRecipeService.registerRecipeSaveByGpt(user.getUserId(), multipartFile,uploadFiles,  gpt.getFoodName(), registerRequestDto);

        RegisterRecipe findById = registerRecipeService.findById(saveRegisterRecipeId);

        //then
        assertThat(findById.getComment()).isEqualTo("1줄평");
    }

    @DisplayName("Tpying을 통한 레시피 등록")
    @Test
    void registerRecipeSaveByTyping()  throws Exception {
        //given
        Long saveRegisterRecipeId = registerRecipeSavedByTyping();

        RegisterRecipe findById = registerRecipeService.findById(saveRegisterRecipeId);

        //then
        assertThat(findById.getComment()).isEqualTo("1줄평");
    }

    @DisplayName("등록한 레시피 삭제")
    @Test
    void deleteRegisterRecipe() throws IOException {
        //given
        Long saveRegisterRecipeId = registerRecipeSavedByTyping();

        //when
        registerRecipeService.deleteRegisterRecipe(saveRegisterRecipeId);

        //then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            registerRecipeService.findById(saveRegisterRecipeId);
        });

        assertThat(exception.getMessage()).isEqualTo("Fail: No RegisterRecipe Info");
    }

    @Test
    public void 레시피_음식이름_단건조회() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        RegisterRecipe registerRecipe = registerRecipeService.findByFoodName("음식이름");

        //then
        assertThat(registerRecipe.getFoodName()).isEqualTo("음식이름");
    }

    @DisplayName("카테고리 조회")
    @Test
    void findByCategory() throws IOException {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> category = registerRecipeService.findByCategory("카테고리");

        //then
        assertThat(category.size()).isEqualTo(1);
    }

    @Test
    public void 검색조건_동적쿼리() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> findRegisterRecipes = registerRecipeService.searchDynamicSearching("음식", 0, 0L);

        //then
        assertThat(findRegisterRecipes.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_전체조회() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();

        //then
        assertThat(registerRecipes.size()).isEqualTo(1);
    }

    @DisplayName("조회수 증가")
    @Test
    void increaseViewCount() throws IOException {
        //given
        Long savedByTyping = registerRecipeSavedByTyping();
        RegisterRecipe registerRecipe = registerRecipeService.findById(savedByTyping);

        //when
        List<RegisterRecipe> registerRecipes = registerRecipeService.increaseViewCount(registerRecipe.getRegisterId());


        //then
        for (RegisterRecipe recipe : registerRecipes) {
            log.info("recipe.getViewCount() = {}", recipe.getViewCount());
        }
        assertThat(registerRecipes.size()).isEqualTo(1);
        assertThat(registerRecipes.get(0).getViewCount()).isEqualTo(1);
    }


    @Test
    public void 레시피_좋아요_Top10() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> top10RegisterRecipeLike = registerRecipeService.findTop10Like();

        //then
        assertThat(top10RegisterRecipeLike.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_조회수_Top10() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> top10RegisterRecipeView = registerRecipeService.findTop10View();

        //then
        assertThat(top10RegisterRecipeView.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_평점순_Top10() throws Exception {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> top10RegisterRecipeRatingScore = registerRecipeService.findTop10RatingScore();

        //then
        assertThat(top10RegisterRecipeRatingScore.size()).isEqualTo(1);
    }

    @DisplayName("최근에 등록한 레시피 검색")
    @Test
    void findTop10RecentRegister() throws IOException {
        //given
        registerRecipeSavedByTyping();

        //when
        List<RegisterRecipe> top10RecentRegister = registerRecipeService.findTop10RecentRegister();

        //then
        assertThat(top10RecentRegister.size()).isEqualTo(1);
    }

    @Test
    public void 연령별_추천() throws Exception {
        //given
        User userAge10 = User.createUser("tester1", "testId123", "test123", 10, null, Collections.singletonList(Authority.ROLE_USER.name()));
        User userAge20 = User.createUser("tester2", "testId456", "test456", 20, null, Collections.singletonList(Authority.ROLE_USER.name()));
        em.persist(userAge10);
        em.persist(userAge20);

        Gpt gpt1 = createGpt(userAge10);
        Gpt gpt2 = createGpt(userAge20);

        testRecommendByAge1(userAge10, gpt1);
        testRecommendByAge2(userAge20, gpt2);

        //when
        List<String> recommendByAge = registerRecipeService.RecommendByAge(15);

        //then
        assertThat(recommendByAge.size()).isEqualTo(10);
    }

    private Gpt createGpt(User user) {
        Gpt gpt = Gpt.createGpt("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", user);
        em.persist(gpt);

        return gpt;
    }

    private void testRecommendByAge1(User user, Gpt gpt) {
        for (int i = 0; i < 15; i++) {
            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe("10대가 좋아하는 음식" + i,   "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                    (long) 20 + i, 20 + i, 5.0, 1, null, null,user, gpt);
            em.persist(registerRecipe);
        }
    }

    private void testRecommendByAge2(User user, Gpt gpt) {
        for (int i = 0; i < 15; i++) {
            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe("20대가 좋아하는 음식" + i, "우유랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                    (long) 50 + i, 50 + i, 4.0, 1, null,null, user, gpt);
            em.persist(registerRecipe);
        }
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
}