package samdasu.recipt.domain.repository.Gpt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.domain.entity.Gpt;
import samdasu.recipt.domain.entity.User;

import java.util.List;

import static samdasu.recipt.domain.entity.QGpt.gpt;

@Repository
@RequiredArgsConstructor
public class GptRepositoryImpl implements GptCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findByFoodNameAndUer(String foodName, User user) {
        return queryFactory
                .select(gpt.foodName)
                .from(gpt)
                .join(gpt.user)
                .where(gpt.foodName.eq(foodName).and(gpt.user.eq(user)))
                .fetch();
    }
}
