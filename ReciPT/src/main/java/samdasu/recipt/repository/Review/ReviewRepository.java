package samdasu.recipt.repository.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {


    Optional<Review> findById(Long reviewId);

    Review findByTitle(String title); //제목 조회
    
}
