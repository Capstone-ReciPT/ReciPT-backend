package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.Review.RegisterRecipeReviewResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.Review;
import samdasu.recipt.domain.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {
    private final ReviewService reviewService;

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
    public void deleteReview(@AuthenticationPrincipal UserResponseDto userResponseDto
            , @RequestParam(value = "reviewId") Long reviewId) {
        reviewService.delete(reviewId);
    }

    @GetMapping("/recipe/{id}")
    public Result recipeReviewInfo(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.findRecipeReviews(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/{id}")
    public Result registerRecipeReviewInfo(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
        List<Review> registerRecipeReviews = reviewService.findRegisterRecipeReviews(recipeId);

        List<RegisterRecipeReviewResponseDto> collect = getRegisterRecipeReviewResponseDtos(registerRecipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/recipe/sort/like/{id}")
    public Result sortRecipeReviewByLikeCount(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.recipeOrderByLike(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/sort/like/{id}")
    public Result sortRegisterRecipeReviewByLikeCount(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
        List<Review> registerRecipeReviews = reviewService.registerOrderByLike(recipeId);

        List<RegisterRecipeReviewResponseDto> collect = getRegisterRecipeReviewResponseDtos(registerRecipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/recipe/sort/create/{id}")
    public Result sortRecipeReviewByCreateDate(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
        List<Review> recipeReviews = reviewService.recipeOrderByCreateDate(recipeId);

        List<RecipeReviewResponseDto> collect = getRecipeReviewResponseDtos(recipeReviews);

        return new Result(collect.size(), collect);
    }

    @GetMapping("/register/sort/create/{id}")
    public Result sortRegisterRecipeReviewByCreateDate(@AuthenticationPrincipal UserResponseDto userResponseDto, @PathVariable("id") Long recipeId) {
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
