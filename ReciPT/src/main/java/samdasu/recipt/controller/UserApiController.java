//package samdasu.recipt.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import samdasu.recipt.controller.dto.User.UserResponseDto;
//import samdasu.recipt.controller.dto.User.UserSignUpDto;
//import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
//import samdasu.recipt.entity.User;
//import samdasu.recipt.service.UserService;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class UserApiController {
//    private final UserService userService;
//
//    @PostMapping("/signup")
//    public UserResponseDto saveUser(@RequestBody @Valid UserSignUpDto userSignUpDto) {
//        Long joinMember = userService.join(userSignUpDto);
//
//        User findUser = userService.findOne(joinMember);
//        return new UserResponseDto(findUser);
//    }
//
//    @GetMapping("/user")
//    public Result userInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
//        User findUser = userService.findOne(userResponseDto.getUserId());
//
//        log.info("user.getUsername() = " + findUser.getUsername());
//        log.info("user.getLoginId() = " + findUser.getLoginId());
//
//        UserResponseDto createUserResponseDto = UserResponseDto.createUserResponseDto(findUser, findUser.getUserId());
//        List<UserResponseDto> userAllergies = findUser.getUserAllergies().stream()
//                .map(userAllergy -> createUserResponseDto)
//                .collect(Collectors.toList());
//        List<UserResponseDto> userReviews = findUser.getReviews().stream()
//                .map(review -> createUserResponseDto)
//                .collect(Collectors.toList());
//
//        for (UserResponseDto userReview : userReviews) {
//            log.info("user.getReviewTitle() = " + userReview.getReviewTitle());
//        }
//        return new Result(userAllergies.size(), userReviews.size(), new UserResponseDto(findUser, findUser.getUserId()));
//    }
//
//    @PostMapping("/edit")
//    public UserResponseDto updateUser(@AuthenticationPrincipal UserResponseDto userResponseDto,
//                                      @RequestBody @Valid UserUpdateRequestDto request) {
//
//        userService.update(userResponseDto.getUserId(), request);
//        User findUser = userService.findOne(userResponseDto.getUserId());
//        return new UserResponseDto(findUser, findUser.getUserId());
//    }
//
//    /**
//     * 레시피 등록 조회
//     */
//
//    /**
//     * 작성한 리뷰 조회
//     */
//
//    /**
//     * 좋아요 음식 조회
//     */
//
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private int allergyCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
//        private int reviewCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
//        private T data;
//    }
//
//
//}
