package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.controller.dto.User.UserSignUpDto;
import samdasu.recipt.controller.dto.User.UserUpdateRequestDto;
import samdasu.recipt.entity.User;
import samdasu.recipt.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto saveUser(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        Long joinMember = userService.join(userSignUpDto);

        User findUser = userService.findOne(joinMember);
        return new UserResponseDto(findUser);
    }

    //    @GetMapping("/user")
//    public UserResponseDto userInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
//        User findUser = userService.findOne(userResponseDto.getUserId());
//
//        System.out.println("user.getUsername() = " + findUser.getUsername());
//        System.out.println("user.getLoginId() = " + findUser.getLoginId());
//
//        return new UserResponseDto(findUser, findUser.getUserId());
//    }
    @GetMapping("/user")
    public Result userInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {
        User findUser = userService.findOne(userResponseDto.getUserId());

        System.out.println("user.getUsername() = " + findUser.getUsername());
        System.out.println("user.getLoginId() = " + findUser.getLoginId());

        UserResponseDto createUserResponseDto = UserResponseDto.createUserResponseDto(findUser, findUser.getUserId());
        List<UserResponseDto> userAllergies = findUser.getUserAllergies().stream()
                .map(userAllergy -> createUserResponseDto)
                .collect(Collectors.toList());
        List<UserResponseDto> userReviews = findUser.getReviews().stream()
                .map(review -> createUserResponseDto)
                .collect(Collectors.toList());

        for (UserResponseDto userReview : userReviews) {
            System.out.println("userReview.getUsername() = " + userReview.getUsername());
        }
        return new Result(userAllergies.size(), userReviews.size(), new UserResponseDto(findUser, findUser.getUserId()));
    }

    @PostMapping("/edit")
    public UserResponseDto updateUser(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                      @RequestBody @Valid UserUpdateRequestDto request) {

        userService.update(userResponseDto.getUserId(), request);
        User findUser = userService.findOne(userResponseDto.getUserId());
        return new UserResponseDto(findUser, findUser.getUserId());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int allergyCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private int reviewCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }


}
