package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Gpt;

import java.util.Optional;

public interface GptRepository extends JpaRepository<Gpt, Long> {
    Optional<Gpt> findByFoodName(String foodName);

}
