package samdasu.recipt.domain.repository.Gpt;

import samdasu.recipt.domain.entity.Gpt;
import samdasu.recipt.domain.entity.User;

import java.util.List;

public interface GptCustomRepository {
    List<String> findByFoodNameAndUer(String foodName, User user);
}
