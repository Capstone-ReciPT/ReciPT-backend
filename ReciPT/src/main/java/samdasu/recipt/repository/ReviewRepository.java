package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
