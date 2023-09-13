package samdasu.recipt.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.Gpt;

import java.util.Optional;

public interface GptRepository extends JpaRepository<Gpt, Long> {
    Optional<Gpt> findByFoodName(String foodName);

}
