package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import samdasu.recipt.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String loginId;
    private String password;
    private String userAllergy;

    private List<HeartDto> hearts;
    private List<ReviewRequestDto> reviews;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        userName = user.getUserName();
        loginId = user.getLoginId();
        password = user.getPassword();
        userAllergy = user.getUserAllergy();

        user.getHearts().stream()
                .map(heart -> new HeartDto(heart))
                .collect(Collectors.toList());
        reviews = user.getReviews().stream()
                .map(review -> new ReviewRequestDto(review))
                .collect(Collectors.toList());
    }
}
