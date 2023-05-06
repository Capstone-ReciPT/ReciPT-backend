package samdasu.recipt.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRequestDto {
    private byte[] thumbnailImage;
    @NotNull
    private String title;
    @NotNull
    private String comment;
    @NotNull
    private String category;

    public RegisterRequestDto(byte[] thumbnailImage, String title, String comment, String category) {
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.comment = comment;
        this.category = category;
    }

    public static RegisterRequestDto createGptDto(byte[] thumbnailImage, String title, String comment, String category) {
        return new RegisterRequestDto(thumbnailImage, title, comment, category);
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
