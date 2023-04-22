package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop10ViewCountBy(); //조회 수 탑10

    Optional<Review> findById(Long reviewId);

    Review findByTitle(String title); //제목 조회


//    Optional<Review> findByUserName(String userName);//글쓴이 조회

}
