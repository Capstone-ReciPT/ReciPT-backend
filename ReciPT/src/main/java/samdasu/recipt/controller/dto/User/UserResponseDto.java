package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
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

    private List<DbHeartDto> dbHeartDto;
    private List<GptHeartDto> gptHeartDto;
    private List<ReviewRequestDto> reviews;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        userName = user.getUserName();
        loginId = user.getLoginId();
        password = user.getPassword();
        userAllergy = user.getUserAllergy();

        dbHeartDto = user.getHearts().stream()
                .map(heart -> new DbHeartDto(heart))
                .collect(Collectors.toList());
        gptHeartDto = user.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
        reviews = user.getReviews().stream()
                .map(review -> new ReviewRequestDto(review))
                .collect(Collectors.toList());
    }
}
