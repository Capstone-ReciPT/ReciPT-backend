package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.RecentSearch.DbRecentSearchDto;
import samdasu.recipt.controller.dto.RecentSearch.GptRecentSearchDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.RecentSearch;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;
import samdasu.recipt.repository.RecentSearchRepository;
import samdasu.recipt.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecentSearchService {
    private final RecentSearchRepository recentSearchRepository;
    private final UserRepository userRepository;
    private final DbRecipeRepository dbRecipeRepository;
    private final GptRecipeRepository gptRecipeRepository;

    @Transactional
    public void insertDbSearchInfo(DbRecentSearchDto dbRecentSearchDto) {
        User user = getUser(dbRecentSearchDto.getUserId());
        DbRecipe dbRecipe = getDbRecipe(dbRecentSearchDto.getDbRecipeId());

        checkLastModifiedDate();

        if (recentSearchRepository.findByUserAndDbRecipe(user, dbRecipe).isEmpty()) {
            RecentSearch recentSearch = RecentSearch.createDbRecentSearch(user, dbRecipe, dbRecipe.getDbFoodName());
            recentSearchRepository.save(recentSearch);
        }
    }

    @Transactional
    public void deleteDbSearchInfo(DbRecentSearchDto dbRecentSearchDto) {
        User user = getUser(dbRecentSearchDto.getUserId());
        DbRecipe dbRecipe = getDbRecipe(dbRecentSearchDto.getDbRecipeId());

        RecentSearch recentSearch = recentSearchRepository.findByUserAndDbRecipe(user, dbRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found recentSearch"));

        recentSearchRepository.delete(recentSearch);
    }

    @Transactional
    public void insertGptSearchInfo(GptRecentSearchDto gptRecentSearchDto) {
        User user = getUser(gptRecentSearchDto.getUserId());
        GptRecipe gptRecipe = getGptRecipe(gptRecentSearchDto.getGptRecipeId());

        checkLastModifiedDate();

        if (recentSearchRepository.findByUserAndGptRecipe(user, gptRecipe).isEmpty()) {
            RecentSearch recentSearch = RecentSearch.createGptRecentSearch(user, gptRecipe, gptRecipe.getGptFoodName());
            recentSearchRepository.save(recentSearch);
        }
    }

    @Transactional
    public void deleteGptSearchInfo(GptRecentSearchDto gptRecentSearchDto) {
        User user = getUser(gptRecentSearchDto.getUserId());
        GptRecipe gptRecipe = getGptRecipe(gptRecentSearchDto.getGptRecipeId());

        RecentSearch recentSearch = recentSearchRepository.findByUserAndGptRecipe(user, gptRecipe)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found recentSearch"));

        recentSearchRepository.delete(recentSearch);
    }

    public List<RecentSearch> findRecentSearches(Long userId) {
        User user = getUser(userId);
        List<RecentSearch> findRecentSearch = recentSearchRepository.findByUser(user);
        return findRecentSearch;
    }


    private User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return user;
    }

    private DbRecipe getDbRecipe(Long dbRecentSearchDto) {
        DbRecipe dbRecipe = dbRecipeRepository.findById(dbRecentSearchDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No DbRecipe Info"));
        return dbRecipe;
    }

    private GptRecipe getGptRecipe(Long gptRecentSearchDto) {
        GptRecipe gptRecipe = gptRecipeRepository.findById(gptRecentSearchDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No GptRecipe Info"));
        return gptRecipe;
    }

    private void checkLastModifiedDate() {
        if (recentSearchRepository.countRecentSearchBy() >= 10) {
            //last_modified 가장 오래된 row 삭제
            List<RecentSearch> recentSearches = recentSearchRepository.findAll();
            LocalDateTime temp = recentSearches.get(0).getLastModifiedDate();
            RecentSearch oldModifiedDate = null;
            for (int i = 1; i < recentSearches.size(); i++) {
                if (temp.compareTo(recentSearches.get(i).getLastModifiedDate()) < 0) {
                    temp = recentSearches.get(i).getLastModifiedDate();
                    oldModifiedDate = recentSearches.get(i);
                }
            }
            recentSearchRepository.delete(oldModifiedDate);
        }
    }

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
}
