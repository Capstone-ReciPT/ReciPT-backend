package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
