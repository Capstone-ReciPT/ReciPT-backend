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
    private Long id;
    private String username;
    private String loginId;
    private String password;
    private String userAllergy;

    private List<AllergyInfoDto> allergyInfos;

    private List<LikeDto> likes;

    private List<ReviewDto> reviews;

    public UserResponseDto(User user) {
        this.id = user.getUserId();
        this.username = user.getUserName();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.userAllergy = user.getUserAllergy();
        allergyInfos = user.getAllergyInfos().stream()
                .map(allergyInfo -> new AllergyInfoDto(allergyInfo))
                .collect(Collectors.toList());
        likes = user.getLikes().stream()
                .map(like -> new LikeDto(like))
                .collect(Collectors.toList());
        reviews = user.getReviews().stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
    }
}
