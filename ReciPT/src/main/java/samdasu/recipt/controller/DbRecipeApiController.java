package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.Db.DbResponseDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.service.DbRecipeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DbRecipeApiController {
    private final DbRecipeService dbRecipeService;

    @GetMapping("/db/{id}")
    public Result dbRecipeInfo(@PathVariable("id") Long dbRecipeId) {
        //조회수 증가
        dbRecipeService.IncreaseViewCount(dbRecipeId);

        //db 레시피 조회
        DbRecipe findDbRecipe = dbRecipeService.findOne(dbRecipeId);
        DbResponseDto dbResponseDto = DbResponseDto.createDbResponseDto(findDbRecipe, findDbRecipe.getDbRecipeId());

        List<DbResponseDto> dbHearts = findDbRecipe.getHearts().stream()
                .map(heart -> dbResponseDto)
                .collect(Collectors.toList());
        List<DbResponseDto> dbReviews = findDbRecipe.getReview().stream()
                .map(review -> dbResponseDto)
                .collect(Collectors.toList());
        return new Result(dbHearts.size(), dbReviews.size(), new DbResponseDto(findDbRecipe, findDbRecipe.getDbRecipeId()));
    }
    

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int heartCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private int reviewCount; //특정 List의 개수 (ex. 사용자가 쓴 리뷰 개수)
        private T data;
    }
}
