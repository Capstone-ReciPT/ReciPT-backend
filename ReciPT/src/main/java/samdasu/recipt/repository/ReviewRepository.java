package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop10ViewCountBy();

    Review findOne(Long reviewId);
    //제목 조회
    //글쓴이 조회


    //조회수 조회

}
