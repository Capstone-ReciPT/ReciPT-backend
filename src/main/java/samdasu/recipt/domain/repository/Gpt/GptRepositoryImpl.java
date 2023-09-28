package samdasu.recipt.domain.repository.Gpt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.domain.entity.Gpt;

import java.util.List;

import static samdasu.recipt.domain.entity.QGpt.gpt;
import static samdasu.recipt.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class GptRepositoryImpl implements GptCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findByFoodNameAndUerId(String foodName, Long userId) {
        return queryFactory.select(gpt.foodName)
                .from(gpt)
                .join(user)
                .where(gpt.foodName.eq(foodName).and(gpt.user.userId.eq(userId)))
                .fetch();
    }
}
