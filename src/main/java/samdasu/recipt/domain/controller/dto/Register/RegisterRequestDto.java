package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "1줄평 해주세요")
    private String comment;
    @NotBlank(message = "카테고리를 입력해주세요")
    private String category;
    private String thumbnailImage;
    private List<String> image;


    public RegisterRequestDto(String title, String comment, String category, String thumbnailImage, List<String> image) {
        this.title = title;
        this.comment = comment;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        this.image = image;
    }

    public static RegisterRequestDto createRegisterRequestDto(String title, String comment, String category, String thumbnailImage, List<String> image) {
        return new RegisterRequestDto(title, comment, category, thumbnailImage, image);
    }


}
/**
 * // 레시피 테이블
 * 요청은 없고 응답값은 레시피 등록과 동일
 * 업데이트 dto를 통해: 좋아요, 평점, 조회수 변경
 * <p>
 * <p>
 * 레시피 등록
 * //레시피 업로드
 * 1. 썸네일
 * 2. 제목
 * 3. 1줄 설명
 * 4. 카테고리
 * 5. 식재료
 * 6. 이미지
 * 7. 조리과정
 * <p>
 * //레시피 등록한거 보여주기
 * 1. 썸네일
 * 2. 음식 이름
 * 3. 제목
 * 4. 1줄 설명
 * 5. 카테고리
 * 6. 식재료
 * 7. 이미지
 * 8. 조리과정
 * 9. 평점
 * 10. 좋아요
 */
