package samdasu.recipt.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samdasu.recipt.controller.dto.RecentSearch.DbRecentSearchDto;
import samdasu.recipt.controller.dto.RecentSearch.GptRecentSearchDto;
import samdasu.recipt.controller.dto.User.UserResponseDto;
import samdasu.recipt.entity.RecentSearch;
import samdasu.recipt.service.RecentSearchService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecentSearchApiController {
    private final RecentSearchService recentSearchService;

    @GetMapping("/search")
    public Result recentSearchInfo(@AuthenticationPrincipal UserResponseDto userResponseDto) {

        List<RecentSearch> recentSearches = recentSearchService.findRecentSearches(userResponseDto.getUserId());

        List<String> collect = recentSearches.stream()
                .map(recentSearch -> recentSearch.getRecentSearchFoodName())
                .collect(Collectors.toList());

        log.info("collect.size() = " + collect.size());

        return new Result(recentSearches.size(), collect);
    }

    /**
     * 최근 검색어 삭제
     * - id 값이 null이라 nullpoint 난다...
     */
    @PostMapping("/search/{id}")
    public Result deleteRecentSearchInfo(@AuthenticationPrincipal UserResponseDto userResponseDto,
                                         @PathVariable("id") Long deleteId) {
        boolean deleteComplete = false;
        List<RecentSearch> recentSearches = recentSearchService.findRecentSearches(userResponseDto.getUserId());

        for (int i = 0; i < recentSearches.size(); i++) {
            if (recentSearches.get(i).getDbRecipe().getDbRecipeId() != null) {
                if (recentSearches.get(i).getDbRecipe().getDbRecipeId() == deleteId) {
                    DbRecentSearchDto dbRecentSearchDto = DbRecentSearchDto.createDbRecentSearchDto(userResponseDto.getUserId(), deleteId);
                    recentSearchService.deleteDbSearchInfo(dbRecentSearchDto);
                    break;
                }

            }
            if (recentSearches.get(i).getGptRecipe().getGptRecipeId() != null) {
                if (recentSearches.get(i).getGptRecipe().getGptRecipeId() == deleteId) {
                    GptRecentSearchDto gptRecentSearchDto = GptRecentSearchDto.createGptRecentSearchDto(userResponseDto.getUserId(), deleteId);
                    recentSearchService.deleteGptSearchInfo(gptRecentSearchDto);
                    break;
                }

            }
        }
//        List<Long> dbRecipeId = recentSearches.stream()
//                .map(recentSearch -> recentSearch.getDbRecipe().getDbRecipeId())
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < dbRecipeId.size(); i++) {
//            if (dbRecipeId.get(i) == deleteId) {
//                DbRecentSearchDto dbRecentSearchDto = DbRecentSearchDto.createDbRecentSearchDto(userResponseDto.getUserId(), deleteId);
//                recentSearchService.deleteDbSearchInfo(dbRecentSearchDto);
//                deleteComplete = true;
//                break;
//            }
//        }
//
//        if (deleteComplete == false) {
//            List<Long> gptRecipeId = recentSearches.stream()
//                    .map(recentSearch -> recentSearch.getGptRecipe().getGptRecipeId())
//                    .collect(Collectors.toList());
//            for (int i = 0; i < gptRecipeId.size(); i++) {
//                if (gptRecipeId.get(i) == deleteId) {
//                    GptRecentSearchDto gptRecentSearchDto = GptRecentSearchDto.createGptRecnetSearchDto(userResponseDto.getUserId(), deleteId);
//                    recentSearchService.deleteGptSearchInfo(gptRecentSearchDto);
//                    deleteComplete = true;
//                    break;
//                }
//            }
//        }
//
//        if (deleteComplete == false) {
//            log.info("최근 검색어 delete 실패!");
//        }


        List<String> collect = recentSearches.stream()
                .map(recentSearch -> recentSearch.getRecentSearchFoodName())
                .collect(Collectors.toList());

        log.info("collect.size() = " + collect.size());

        return new Result(recentSearches.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int recentSearchCount; //최근 검색어 개수
        private T data;
    }

}
