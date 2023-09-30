package samdasu.recipt.domain.controller.dto.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLogoutDto {
    @NotEmpty(message = "잘못된 요청입니다.")
    private String accessToken;
    @NotEmpty(message = "잘못된 요청입니다.")
    private String refreshToken;
}
