package samdasu.recipt.domain.controller.dto.Recipe;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.domain.controller.dto.Review.RecipeReviewResponseDto;
import samdasu.recipt.domain.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecipeResponseDto {

    private Long recipeId;

    private String foodName;

    private String ingredient;

    private String category; // 레시피 탭(카테고리)

    private String thumbnailImage; //홈화면,마이페이지 좋아요

    private String context; //레시피 볼 때

    private String image; //레시피 볼 때
    private Integer likeCount; //다 필요
    private Long viewCount; //조회수 - 홈화면(탑 10)
    private Double ratingScore;
    private Integer ratingPeople;

    private List<RecipeReviewResponseDto> reviewResponseDtos;

    private List<RecipeHeartDto> heartDtos;


    public RecipeResponseDto(Recipe recipe) {
        recipeId = recipe.getRecipeId();
        foodName = recipe.getFoodName();
        ingredient = recipe.getIngredient();
        category = recipe.getCategory();
        thumbnailImage = recipe.getThumbnailImage();
        context = recipe.getContext();
        image = recipe.getImage();
        likeCount = recipe.getLikeCount();
        viewCount = recipe.getViewCount();
        ratingScore = recipe.getRatingScore();
        ratingPeople = recipe.getRatingPeople();
        reviewResponseDtos = recipe.getReview().stream()
                .map(RecipeReviewResponseDto::new)
                .collect(Collectors.toList());
        heartDtos = recipe.getHearts().stream()
                .map(heart -> new RecipeHeartDto(heart))
                .collect(Collectors.toList());
    }

    public static RecipeResponseDto createRecipeResponseDto(Recipe recipe) {
        return new RecipeResponseDto(recipe);
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