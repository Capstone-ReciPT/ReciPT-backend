package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.controller.dto.Review.ReviewUpdateRequestDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.Review.ReviewRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;


    @Test
    @Rollback(value = false)
    public void 리뷰_좋아요_증가() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
        Review review = createRecipeReview(user, recipe);

        //when
        reviewRepository.addReviewLikeCount(review);
        //then
        assertEquals(review.getLikeCount(), 1);
    }

    @Test
    @Rollback(value = false)
    public void 리뷰_좋아요_감소() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
        Review review = createRecipeReview(user, recipe);

        //when
        reviewRepository.subReviewLikeCount(review);
        //then
        assertEquals(review.getLikeCount(), -1);
    }

    /**
     * 리뷰 저장: 이미지 파일 먼저 저장하는 로직 완성해야함!
     */
    @Test
    @Rollback(value = false)
    public void 리뷰_저장() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("계란찜은 밥이랑 먹어요", 4.0);

        //when
        reviewService.saveRecipeReview(user.getUserId(), recipe.getRecipeId(), reviewRequestDto);

        //then
        assertThat(reviewRequestDto.getComment()).isEqualTo("계란찜은 밥이랑 먹어요");
    }

    @Test
    public void 리뷰_업데이트() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
        Review review = createRecipeReview(user, recipe);

        ReviewUpdateRequestDto reviewUpdateRequestDto = ReviewUpdateRequestDto.createReviewUpdateRequestDto("changeComment");
        //when
        Long updateReview = reviewService.update(review.getReviewId(), reviewUpdateRequestDto);

        Review findReview = reviewRepository.findById(updateReview).get();
        //then
        assertEquals(findReview.getComment(), "changeComment");
    }


    @Test
    public void 리뷰_삭제() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
        Review review = createRecipeReview(user, recipe);

        //when
        reviewService.delete(review.getReviewId());

        //then
        long count = reviewRepository.count();
        assertThat(count).isEqualTo(4);
    }

    @Test
    public void Review_글쓴이_조회() throws Exception {
        //given

        //when
        List<Review> findReviewsByWriter = reviewService.findReviewByWriter("testerA"); // 'testerA' 찾기

        //then
        assertEquals(findReviewsByWriter.size(), 2);
    }

    @Test
    public void Review_전체조회() throws Exception {
        //given

        //when
        List<Review> reviews = reviewService.findReviews();

        //then
        assertThat(reviews.size()).isEqualTo(4);
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234", 10);
        em.persist(user);

        return user;
    }

    private Recipe createRecipe() {
        Recipe recipe = Recipe.createRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0L, 0, 0.0, 0);
        em.persist(recipe);

        return recipe;
    }

    private Review createRecipeReview(User user, Recipe recipe) {
        Review review = Review.createRecipeReview("새우두부계란찜 후기", 0, 3.0, user, recipe);
        em.persist(review);

        return review;
    }
}