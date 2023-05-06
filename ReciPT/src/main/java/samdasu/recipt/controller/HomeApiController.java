//package samdasu.recipt.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import samdasu.recipt.controller.dto.Db.DbShortResponseDto;
//import samdasu.recipt.controller.dto.Gpt.GptShortResponseDto;
//import samdasu.recipt.entity.DbRecipe;
//import samdasu.recipt.service.DbRecipeService;
//import samdasu.recipt.service.GptRecipeService;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class HomeApiController {
//    private final DbRecipeService dbRecipeService;
//    private final GptRecipeService gptRecipeService;
//
//
//    /**
//     * 좋아요 탑 10
//     */
//    @GetMapping("/home")
//    public Result Top10Like() {
//        //db 탑 10 조회
//        List<DbRecipe> dbTop10Like = dbRecipeService.findTop10LikeCount();
//
//        Map<Integer, DbShortResponseDto> dbInfo = new HashMap<>();
//
//        for (int i = 0; i < dbTop10Like.size(); i++) {
//            DbShortResponseDto shortInfo = DbShortResponseDto.createShortInfo(dbTop10Like.get(i));
//            List<String> dbFoodName = dbTop10Like.stream()
//                    .map(dbRecipe -> dbRecipe.getDbFoodName())
//                    .collect(Collectors.toList());
//            List<String> dbThumbnail = dbTop10Like.stream()
//                    .map(dbRecipe -> dbRecipe.getThumbnailImage())
//                    .collect(Collectors.toList());
//            List<Integer> dbLikeCount = dbTop10Like.stream()
//                    .map(dbRecipe -> dbRecipe.getDbLikeCount())
//                    .collect(Collectors.toList());
//            List<Double> dbRatingScore = dbTop10Like.stream()
//                    .map(dbRecipe -> dbRecipe.getDbRatingScore())
//                    .collect(Collectors.toList());
//            List<Integer> dbRatingPeople = dbTop10Like.stream()
//                    .map(dbRecipe -> dbRecipe.getDbRatingPeople())
//                    .collect(Collectors.toList());
//            dbInfo.put(i, shortInfo);
//        }
//
//        //gpt 탑 10 조회
//        List<GptRecipe> gptTop10Like = gptRecipeService.findTop10LikeCount();
//
//        Map<Integer, GptShortResponseDto> gptInfo = new HashMap<>();
//
//        for (int i = 0; i < gptTop10Like.size(); i++) {
//            GptShortResponseDto shortInfo = GptShortResponseDto.creatShortInfo(gptTop10Like.get(i));
//            List<String> gptFoodName = gptTop10Like.stream()
//                    .map(gptRecipe -> gptRecipe.getGptFoodName())
//                    .collect(Collectors.toList());
//            List<Integer> gptLikeCount = gptTop10Like.stream()
//                    .map(gptRecipe -> gptRecipe.getGptLikeCount())
//                    .collect(Collectors.toList());
//            List<Double> gptRatingScore = gptTop10Like.stream()
//                    .map(gptRecipe -> gptRecipe.getGptRatingScore())
//                    .collect(Collectors.toList());
//            List<Integer> gptRatingPeople = gptTop10Like.stream()
//                    .map(gptRecipe -> gptRecipe.getGptRatingPeople())
//                    .collect(Collectors.toList());
//            gptInfo.put(i, shortInfo);
//        }
//        return new Result(dbInfo.get(0), dbInfo.get(1), dbInfo.get(2), dbInfo.get(3), dbInfo.get(4), dbInfo.get(5), dbInfo.get(6), dbInfo.get(7), dbInfo.get(8), dbInfo.get(9));
//
////        return new Result(gptInfo.get(0), gptInfo.get(1), gptInfo.get(2), gptInfo.get(3), gptInfo.get(4), gptInfo.get(5), gptInfo.get(6), gptInfo.get(7), gptInfo.get(8), gptInfo.get(9));
//    }
//
//
//    /**
//     * 리스트 형식으로 컬럼단위로 나옴
//     */
////    @GetMapping("/home")
////    public Result Top10Like() {
////        //db 탑 10 조회
////        List<DbRecipe> dbTop10Like = dbRecipeService.findTop10LikeCount();
////
////        List<String> dbFoodName = dbTop10Like.stream()
////                .map(dbRecipe -> dbRecipe.getDbFoodName())
////                .collect(Collectors.toList());
////        List<String> dbThumbnail = dbTop10Like.stream()
////                .map(dbRecipe -> dbRecipe.getThumbnailImage())
////                .collect(Collectors.toList());
////        List<Integer> dbLikeCount = dbTop10Like.stream()
////                .map(dbRecipe -> dbRecipe.getDbLikeCount())
////                .collect(Collectors.toList());
////        List<Double> dbRatingScore = dbTop10Like.stream()
////                .map(dbRecipe -> dbRecipe.getDbRatingScore())
////                .collect(Collectors.toList());
////        List<Integer> dbRatingPeople = dbTop10Like.stream()
////                .map(dbRecipe -> dbRecipe.getDbRatingPeople())
////                .collect(Collectors.toList());
////
////        //gpt 탑 10 조회
////        List<GptRecipe> gptTop10Like = gptRecipeService.findTop10LikeCount();
////
////        List<String> gptFoodName = gptTop10Like.stream()
////                .map(gptRecipe -> gptRecipe.getGptFoodName())
////                .collect(Collectors.toList());
////        List<Integer> gptLikeCount = gptTop10Like.stream()
////                .map(gptRecipe -> gptRecipe.getGptLikeCount())
////                .collect(Collectors.toList());
////        List<Double> gptRatingScore = gptTop10Like.stream()
////                .map(gptRecipe -> gptRecipe.getGptRatingScore())
////                .collect(Collectors.toList());
////        List<Integer> gptRatingPeople = gptTop10Like.stream()
////                .map(gptRecipe -> gptRecipe.getGptRatingPeople())
////                .collect(Collectors.toList());
////
////
////        return new Result(dbFoodName, dbThumbnail, dbLikeCount, dbRatingScore, dbRatingPeople, gptFoodName, gptLikeCount, gptRatingScore, gptRatingPeople);
////    }
//
//    /**
//     * 조회수 탑 10
//     */
//
//
//    /**
//     * 평점 탑 10
//     */
//
//    /**
//     * 새로운 레시피 등록
//     */
//
////    @Data
////    @AllArgsConstructor
////    static class Result<T> {
////        private T dbFoodName;
////        private T dbThumbnail;
////        private T dbLikeCount;
////        private T dbRatingScore;
////        private T dbRatingPeople;
////        private T gptFoodName;
////        private T gptLikeCount;
////        private T gptRatingScore;
////        private T gptRatingPeople;
////    }
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T data1;
//        private T data2;
//        private T data3;
//        private T data4;
//        private T data5;
//        private T data6;
//        private T data7;
//        private T data8;
//        private T data9;
//        private T data10;
//
//    }
//
//}
