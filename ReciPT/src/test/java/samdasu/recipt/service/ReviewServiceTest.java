package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.controller.dto.Review.ReviewUpdateRequestDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
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
    public void 조회수_증가() throws Exception {
        //given
        Review review = createReview();

        //when
        reviewRepository.addReviewViewCount(review);

        //then
        assertEquals(review.getViewCount(), 1);
    }

    @Test
    public void 리뷰_좋아요_증가() throws Exception {
        //given
        Review review = createReview();

        //when
        reviewRepository.addReviewLikeCount(review);
        //then
        assertEquals(review.getLikeCount(), 1);
    }

    @Test
    public void 리뷰_좋아요_감소() throws Exception {
        //given
        Review review = createReview();

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
        GptRecipe recipe = createRecipe();
        ReviewRequestDto gptReviewRequestDto = ReviewRequestDto.createGptReviewRequestDto("testerA", "리뷰 제목1", "계란말이 맛있다.", (double) 0, recipe, null);

        //when
        reviewService.saveReview(gptReviewRequestDto);

        //then
        assertThat(gptReviewRequestDto.getTitle()).isEqualTo("tester");
    }

    @Test
    public void 리뷰_업데이트() throws Exception {
        //given
        GptRecipe gptRecipe = createRecipe();
        Review review = createReview();

        ReviewUpdateRequestDto reviewUpdateRequestDto = ReviewUpdateRequestDto.createDbReviewRequestDto("changeTitle", "changeComment");
        //when
        Long updateReview = reviewService.update(review.getReviewId(), reviewUpdateRequestDto);

        Review findReview = reviewRepository.findById(updateReview).get();
        //then
        assertEquals(findReview.getTitle(), "changeTitle");
    }


    @Test
    public void 리뷰_삭제() throws Exception {
        //given
        Review review = createReview();

        //when
        reviewService.delete(review.getReviewId());

        //then
        long count = reviewRepository.count();
        assertThat(count).isEqualTo(18);
    }

    @Test
    public void 리뷰_조회수_탑10() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<Review> top10ViewCount = reviewService.findTop10ViewCount();
        //then

        for (Review review : top10ViewCount) {
            System.out.println("review.getViewCount() = " + review.getViewCount());
        }
    }

    @Test
    public void Review_좋아요_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top10 ###############");
        List<Review> top10LikeCount = reviewService.findTop10LikeCount();
        //then

        for (Review review : top10LikeCount) {
            System.out.println("review.getLikeCount() = " + review.getLikeCount());
        }
    }


    @Test
    public void Review_제목_조회() throws Exception {
        //given
        Review findReview = createReview();

        //when
        Review review = reviewService.findReviewByTitle(findReview.getTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));

        //then
        assertEquals(review.getTitle(), "파스타 후기");
    }

    @Test
    public void Review_글쓴이_조회() throws Exception {
        //given

        //when
        List<Review> findReviewsByWriter = reviewService.findReviewByWriter("testerA"); // 'testerA' 찾기

        //then
        assertEquals(findReviewsByWriter.size(), 2);
        for (Review review : findReviewsByWriter) {
            System.out.println("review.getUser().getUserName() = " + review.getUser().getUsername());
        }
    }

    @Test
    public void Review_전체조회() throws Exception {
        //given

        //when
        List<Review> reviews = reviewService.findAll();

        //then
        assertThat(reviews.size()).isEqualTo(18);
    }

    private Review createReview() {
        User user = User.createUser("testerE", "testE", "E1234");
        em.persist(user);

        Allergy allergy = Allergy.createAllergy("갑각류", "새우");

        GptRecipe gptRecipe = GptRecipe.createGptRecipe("파스타", "면, 소스", "1.삶기 2.먹기", "소스 맛은 기호에 따라 바꾸세요!", 0, 0L, 0.0, 0, allergy);
        em.persist(gptRecipe);

        Review review = Review.createGptReview("파스타 후기", "면이 쫀득쫀득", 0L, 0, user, gptRecipe);
        em.persist(review);
        return review;
    }

    private GptRecipe createRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", "음료수랑 먹으면 맛있어요.", 0, 0L, 0.0, 0, allergy);
        em.persist(gptRecipe);

        return gptRecipe;
    }
}