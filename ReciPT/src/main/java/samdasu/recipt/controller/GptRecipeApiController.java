package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samdasu.recipt.controller.dto.Gpt.GptResponseDto;
import samdasu.recipt.controller.dto.RecentSearch.GptRecentSearchDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.service.GptRecipeService;
import samdasu.recipt.service.RecentSearchService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GptRecipeApiController {
    private final GptRecipeService gptRecipeService;
    private final RecentSearchService recentSearchService;

    @GetMapping("/gpt/{id}")
    public Result gptRecipeInfo(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                @PathVariable("id") Long gptRecipeId) {
        //조회수 증가
        gptRecipeService.IncreaseViewCount(gptRecipeId);

        //최근 검색어 추가
        GptRecentSearchDto gptRecentSearchDto = GptRecentSearchDto.createGptRecentSearchDto(userResponseDto.getUserId(), gptRecipeId);
        recentSearchService.insertGptSearchInfo(gptRecentSearchDto);

        //gpt 레시피 조회
        GptRecipe findGptRecipe = gptRecipeService.findOne(gptRecipeId);
        GptResponseDto gptResponseDto = GptResponseDto.createGptResponseDto(findGptRecipe, findGptRecipe.getGptRecipeId());

        List<GptResponseDto> gptHearts = findGptRecipe.getHearts().stream()
                .map(heart -> gptResponseDto)
                .collect(Collectors.toList());
        List<GptResponseDto> gptReviews = findGptRecipe.getReview().stream()
                .map(review -> gptResponseDto)
                .collect(Collectors.toList());
        return new Result(gptHearts.size(), gptReviews.size(), new GptResponseDto(findGptRecipe, findGptRecipe.getGptRecipeId()));
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int heartCount; //특정 List의 개수
        private int reviewCount; //특정 List의 개수
        private T data;
    }
}
