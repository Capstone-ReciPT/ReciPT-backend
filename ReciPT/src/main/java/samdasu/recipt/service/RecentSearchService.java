//package samdasu.recipt.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import samdasu.recipt.controller.dto.Heart.DbHeartDto;
//import samdasu.recipt.controller.dto.Heart.GptHeartDto;
//import samdasu.recipt.controller.dto.RecentSearchDto;
//import samdasu.recipt.entity.DbRecipe;
//import samdasu.recipt.entity.GptRecipe;
//import samdasu.recipt.entity.RecentSearch;
//import samdasu.recipt.entity.User;
//import samdasu.recipt.exception.ResourceNotFoundException;
//import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
//import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;
//import samdasu.recipt.repository.RecentSearch.RecentSearchRepository;
//import samdasu.recipt.repository.UserRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class RecentSearchService {
//    private final RecentSearchRepository recentSearchRepository;
//    private final UserRepository userRepository;
//    private final DbRecipeRepository dbRecipeRepository;
//    private final GptRecipeRepository gptRecipeRepository;
//
//    @Transactional
//    public void insertSearchInfo(RecentSearchDto recentSearchDto) {
//        User user = userRepository.findById(recentSearchDto.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
//        DbRecipe dbRecipe = dbRecipeRepository.findById(recentSearchDto.getDbRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
//        GptRecipe gptRecipe = gptRecipeRepository.findById(recentSearchDto.getGptRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No GptRecipe Info"));
//
//        if (recentSearchRepository.countRecentSearchBy() >= 10) {
//            //last_modified 가장 오래된 row 삭제
//            List<RecentSearch> recentSearches = recentSearchRepository.findAll();
//            int temp = Integer.parseInt(LocalDateTime.now().toString());
//            System.out.println("temp = " + temp);
//
//            for (int i = 0; i < recentSearches.size(); i++) {
//                int compare = Integer.parseInt(recentSearches.get(i).getLastModifiedDate().toString());
//                if (temp > compare) {
//                    temp = compare;
//                }
//            }
//            String result = String.valueOf(temp);
//            System.out.println("result = " + result);
//
////            recentSearchRepository.delete(oldModifiedDate);
//        }
//
//        if (recentSearchRepository.findByUserAndDbRecipe(user, dbRecipe).isEmpty()) {
//            RecentSearch recentSearch = RecentSearch.createRecentSearch(user, dbRecipe, gptRecipe, dbRecipe.getDbFoodName());
//            recentSearchRepository.save(recentSearch);
//        } else if (recentSearchRepository.findByUserAndGptRecipe(user, gptRecipe).isEmpty()) {
//            RecentSearch recentSearch = RecentSearch.createRecentSearch(user, dbRecipe, gptRecipe, gptRecipe.getGptFoodName());
//            recentSearchRepository.save(recentSearch);
//        }
//    }
//
//
//    @Transactional
//    public void deleteSearchInfo(RecentSearchDto recentSearchDto) {
//        User user = userRepository.findById(recentSearchDto.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
//        DbRecipe dbRecipe = dbRecipeRepository.findById(recentSearchDto.getDbRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
//        GptRecipe gptRecipe = gptRecipeRepository.findById(recentSearchDto.getGptRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No GptRecipe Info"));
//
//        RecentSearch recentSearch = recentSearchRepository.findByUserAndDbRecipeAndGptRecipe(user, dbRecipe, gptRecipe)
//                .orElseThrow(() -> new ResourceNotFoundException("Could not found recentSearch"));
//
//
//        recentSearchRepository.delete(recentSearch);
//    }
//
//
//    private DbRecipe getDbRecipe(DbHeartDto heartDto) {
//        DbRecipe dbRecipe = dbRecipeRepository.findById(heartDto.getDbRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
//        return dbRecipe;
//    }
//
//    private GptRecipe getGptRecipe(GptHeartDto gptHeartDto) {
//        GptRecipe gptRecipe = gptRecipeRepository.findById(gptHeartDto.getGptRecipeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No GptRecipe Info"));
//        return gptRecipe;
//    }
//}
