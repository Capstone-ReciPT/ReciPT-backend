package samdasu.recipt.controller.dto.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserResponseDto {
    @NotBlank
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String loginId;
    @NotNull
    private String password;

    private List<DbHeartDto> dbHeartDto;
    private List<GptHeartDto> gptHeartDto;
    private List<ReviewRequestDto> reviews;

    public UserResponseDto(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        loginId = user.getLoginId();
        password = user.getPassword();

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
