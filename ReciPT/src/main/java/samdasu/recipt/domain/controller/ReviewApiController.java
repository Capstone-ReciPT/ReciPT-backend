package samdasu.recipt.domain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.service.ReviewService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {
    private final ReviewService reviewService;

    /**
     * 리뷰 내용 변경
     * - reviewId로 찾아서 변경해줄려고 했는데... 만약 recipe & 등록한 recipe id가 같으면??
     */
//    @PostMapping("/edit")
//    public Result2 updateReview(@AuthenticationPrincipal UserResponseDto userResponseDto
//            , @RequestParam(value = "reviewId") Long reviewId, @Valid ReviewUpdateRequestDto requestDto) {
//        List<Review> reviews = reviewService.findReviewByWriter(userResponseDto.getUsername());
//        Long updateId = reviewService.update(reviewId, requestDto);
//        Review review = reviewService.findById(updateId);
//
//
//        return new Result2(1, review);
//    }
    @PostMapping("/delete")
    public void deleteReview(@AuthenticationPrincipal UserResponseDto userResponseDto
            , @RequestParam(value = "reviewId") Long reviewId) {
        reviewService.delete(reviewId);
    }


    @Data
    @AllArgsConstructor
    static class Result1<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
        private T profile;
    }

    @Data
    @AllArgsConstructor
    static class Result2<T> {
        private int count; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }
}
