package samdasu.recipt.domain.repository.Gpt;

import samdasu.recipt.domain.entity.Gpt;

import java.util.List;

public interface GptCustomRepository {
    List<String> findByFoodNameAndUerId(String foodName, Long userId);
}
