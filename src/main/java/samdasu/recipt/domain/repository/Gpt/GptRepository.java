package samdasu.recipt.domain.repository.Gpt;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.Gpt;

import java.util.List;
import java.util.Optional;

public interface GptRepository extends JpaRepository<Gpt, Long>, GptCustomRepository {

    Optional<Gpt> findByFoodName(String foodName);

    List<Gpt> findByUser_UserId(Long userId);

    Optional<Gpt> findByUser_UserIdAndGptId(Long userId, Long gptId);
}
