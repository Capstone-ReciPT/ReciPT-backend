//package samdasu.recipt.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import samdasu.recipt.controller.dto.Db.DbResponseDto;
//import samdasu.recipt.controller.dto.RecentSearch.DbRecentSearchDto;
//import samdasu.recipt.controller.dto.User.UserResponseDto;
//import samdasu.recipt.entity.DbRecipe;
//import samdasu.recipt.service.DbRecipeService;
//import samdasu.recipt.service.RecentSearchService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class DbRecipeApiController {
//    private final DbRecipeService dbRecipeService;
//    private final RecentSearchService recentSearchService;
//
//    @GetMapping("/db/{id}")
//    public Result dbRecipeInfo(@AuthenticationPrincipal UserResponseDto userResponseDto,
//                               @PathVariable("id") Long dbRecipeId) {
//        //조회수 증가
//        dbRecipeService.IncreaseViewCount(dbRecipeId);
//
//        //최근 검색어 추가
//        DbRecentSearchDto dbRecentSearchDto = DbRecentSearchDto.createDbRecentSearchDto(userResponseDto.getUserId(), dbRecipeId);
//        recentSearchService.insertDbSearchInfo(dbRecentSearchDto);
//
//        //db 레시피 조회
//        DbRecipe findDbRecipe = dbRecipeService.findOne(dbRecipeId);
//        DbResponseDto dbResponseDto = DbResponseDto.createDbAllInfo(findDbRecipe);
//
//        List<DbResponseDto> dbHearts = findDbRecipe.getHearts().stream()
//                .map(heart -> dbResponseDto)
//                .collect(Collectors.toList());
//        List<DbResponseDto> dbReviews = findDbRecipe.getReview().stream()
//                .map(review -> dbResponseDto)
//                .collect(Collectors.toList());
//        return new Result(dbHearts.size(), dbReviews.size(), new DbResponseDto(findDbRecipe));
//    }
//
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private int heartCount; //특정 List의 개수
//        private int reviewCount; //특정 List의 개수
//        private T data;
//    }
//}
