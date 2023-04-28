package samdasu.recipt.repository.RecentSearch;

import samdasu.recipt.entity.RecentSearch;

public interface RecentSearchCustomRepository {
    void findOldModifiedDate(RecentSearch recentSearch);
}
