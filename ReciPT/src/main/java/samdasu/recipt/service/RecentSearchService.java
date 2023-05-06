package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecentSearchService {

//    private void checkLastModifiedDate() {
//        if (recentSearchRepository.countRecentSearchBy() >= 10) {
//            //last_modified 가장 오래된 row 삭제
//            List<RecentSearch> recentSearches = recentSearchRepository.findAll();
//            LocalDateTime temp = recentSearches.get(0).getLastModifiedDate();
//            RecentSearch oldModifiedDate = null;
//            for (int i = 1; i < recentSearches.size(); i++) {
//                if (temp.compareTo(recentSearches.get(i).getLastModifiedDate()) < 0) {
//                    temp = recentSearches.get(i).getLastModifiedDate();
//                    oldModifiedDate = recentSearches.get(i);
//                }
//            }
//            recentSearchRepository.delete(oldModifiedDate);
//        }
//    }

}
