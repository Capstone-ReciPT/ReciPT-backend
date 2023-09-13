package samdasu.recipt.domain.repository.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    
}
