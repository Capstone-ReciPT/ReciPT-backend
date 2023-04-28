package samdasu.recipt.repository.RecentSearch;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.QRecentSearch;
import samdasu.recipt.entity.RecentSearch;

@Repository
@RequiredArgsConstructor
public class RecentSearchRepositoryImpl implements RecentSearchCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void findOldModifiedDate(RecentSearch recentSearch) {
        queryFactory
                .selectFrom(QRecentSearch.recentSearch)
                .where();


    }

}
