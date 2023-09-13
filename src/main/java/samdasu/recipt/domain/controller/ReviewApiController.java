package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Heart.ReviewHeartDto;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.entity.Heart;
import samdasu.recipt.domain.entity.Review;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.service.HeartService;
import samdasu.recipt.domain.service.ReviewService;
import samdasu.recipt.domain.service.UserService;
import samdasu.recipt.security.config.auth.PrincipalDetails;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final HeartService heartService;

    /**
     * 리뷰 내용 변경
     * - 로그인 한 본인이 쓴 리뷰가 아닌 리뷰는 수정 불가능하다! -> 로직 미생성
     */
//    @PostMapping("/edit")
//    public void updateReview(@AuthenticationPrincipal UserResponseDto userResponseDto
//                             ,@Valid ReviewUpdateRequestDto requestDto) {
//        reviewService.update(userResponseDto.get, requestDto);
//    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteReview(Authentication authentication
            , @RequestParam(value = "reviewId") Long reviewId) {
        boolean isDelete = false;
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User findUser = userService.findById(principal.getUser().getUserId());
        List<Review> reviews = findUser.getReviews();

        for (Review review : reviews) {
            if (review.getReviewId().equals(reviewId)) {
                isDelete = true;
                List<Heart> hearts = review.getHearts();
                for (Heart heart : hearts) {
                    ReviewHeartDto reviewHeartDto = ReviewHeartDto.createRecipeHeartDto(heart);
                    heartService.deleteReviewHeart(reviewHeartDto);
                }
                reviewService.delete(reviewId);
                return ResponseEntity.status(OK).body("리뷰 삭제 완료!");
            }
        }
        return status(FORBIDDEN).body("리뷰 삭제 권한이 없습니다!");
    }

    @GetMapping("/recipe/{id}")
    public Result recipeReviewInfo(@PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.findRecipeReviews(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/{id}")
    public Result registerRecipeReviewInfo(@PathVariable("id") Long recipeId) {
        List<Review> registerRecipeReviews = reviewService.findRegisterRecipeReviews(recipeId);

        List<RegisterRecipeReviewResponseDto> collect = getRegisterRecipeReviewResponseDtos(registerRecipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/recipe/sort/like/{id}")
    public Result sortRecipeReviewByLikeCount(@PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.recipeOrderByLike(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/sort/like/{id}")
    public Result sortRegisterRecipeReviewByLikeCount(@PathVariable("id") Long recipeId) {
        List<Review> registerRecipeReviews = reviewService.registerOrderByLike(recipeId);

        List<RegisterRecipeReviewResponseDto> collect = getRegisterRecipeReviewResponseDtos(registerRecipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/recipe/sort/create/{id}")
    public Result sortRecipeReviewByCreateDate(@PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.recipeOrderByCreateDate(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/sort/create/{id}")
    public Result sortRegisterRecipeReviewByCreateDate(@PathVariable("id") Long recipeId) {
        List<Review> registerRecipeReviews = reviewService.registerOrderByCreateDate(recipeId);

        List<RegisterRecipeReviewResponseDto> collect = getRegisterRecipeReviewResponseDtos(registerRecipeReviews);

        return new Result(collect.size(), collect);
    }

    private List<RecipeReviewResponseDto> getRecipeReviewResponseDtos(List<Review> recipeReviews) {
        return recipeReviews.stream()
                .map(RecipeReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    private List<RegisterRecipeReviewResponseDto> getRegisterRecipeReviewResponseDtos(List<Review> registerRecipeReviews) {
        return registerRecipeReviews.stream()
                .map(RegisterRecipeReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
