package samdasu.recipt.domain.controller.dto.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserReissueDto {
    @NotEmpty(message = "accessToken 입력해주세요")
    private String accessToken;
    @NotEmpty(message = "refreshToken 입력해주세요")
    private String refreshToken;
}
