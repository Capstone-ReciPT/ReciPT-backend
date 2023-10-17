package samdasu.recipt.domain.repository.Gpt;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.Gpt;
import samdasu.recipt.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface GptRepository extends JpaRepository<Gpt, Long>, GptCustomRepository {

    Optional<Gpt> findByFoodName(String foodName);

    List<Gpt> findByUser(User user);

    Optional<Gpt> findByUserAndGptId(User user, Long gptId);
}
