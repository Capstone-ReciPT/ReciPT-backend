//package samdasu.recipt.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import samdasu.recipt.controller.dto.ImageFile.ImageFileRequestDto;
//import samdasu.recipt.controller.dto.ImageFile.ImageFileResponseDto;
//import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
//import samdasu.recipt.entity.Allergy;
//import samdasu.recipt.entity.GptRecipe;
//import samdasu.recipt.entity.Review;
//import samdasu.recipt.entity.User;
//import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;
//import samdasu.recipt.repository.Review.ReviewRepository;
//import samdasu.recipt.repository.UserRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class ReviewServiceTest {
//    @Autowired
//    UserService userService;
//    @Autowired
//    GptRecipeRepository gptRecipeRepository;
//    @Autowired
//    ReviewRepository reviewRepository;
//    @Autowired
//    ReviewService reviewService;
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    @Rollback(value = false)
//    public void 조회수_증가() throws Exception {
//        //given
//        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
//        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
//        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란말이", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
//
//        Review gptReview = Review.createGptReview("계란말이 후기", "단짠단짠", 0, 0, user, gptRecipe);
//
//        userRepository.save(user);
//        gptRecipeRepository.save(gptRecipe);
//        reviewRepository.save(gptReview);
//
//        //when
//        reviewService.increaseViewCount(gptReview.getReviewId());
//
//        //then
//        assertEquals(gptReview.getViewCount(), 1);
//    }
//
//    @Test
//    public void 리뷰_좋아요_증가() throws Exception {
//        //given
//        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
//        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
//        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란말이", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
//
//        Review gptReview = Review.createGptReview("계란말이 후기", "단짠단짠", 0, 0, user, gptRecipe);
//
//        userRepository.save(user);
//        gptRecipeRepository.save(gptRecipe);
//        reviewRepository.save(gptReview);
//
//        //when
//        reviewService.increaseReviewLike(gptReview.getReviewId());
//        //then
//        assertEquals(gptReview.getLikeCount(), 1);
//    }
//
//    @Test
//    public void 리뷰_좋아요_감소() throws Exception {
//        User user = User.createUser("tester1", "testId", "test1234", "shrimp");
//        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
//        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란말이", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
//
//        Review gptReview = Review.createGptReview("계란말이 후기", "단짠단짠", 0, 1, user, gptRecipe);
//
//        userRepository.save(user);
//        gptRecipeRepository.save(gptRecipe);
//        reviewRepository.save(gptReview);
//
//        //when
//        reviewService.decreaseReviewLike(gptReview.getReviewId());
//        //then
//        assertEquals(gptReview.getLikeCount(), 0);
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 리뷰_저장() throws Exception {
//        //given
//        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
//        GptRecipe gptRecipe = GptRecipe.createGptRecipe("계란말이", "계란, 파, 당근", "1.손질 2.굽기 3.먹기", "케찹 추가 맛있어요.", 0, 0, 0.0, 0, allergy);
//
//        ImageFileRequestDto imageFileRequestDto = ImageFileRequestDto.createImageFileRequestDto("qwe-qwe-qwe", "egg-food");
//
//
//        ImageFileResponseDto.createImageFileResponseDto()
//        ReviewRequestDto gptReviewRequestDto = ReviewRequestDto.createGptReviewRequestDto("tester", "리뷰 제목1", "계란말이 맛있다.", gptRecipe, );
//        reviewService.saveReview(gptReviewRequestDto);
//        //when
//
//        //then
//    }
//
//
//}