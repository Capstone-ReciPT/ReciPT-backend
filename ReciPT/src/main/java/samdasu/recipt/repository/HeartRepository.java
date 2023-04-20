package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Heart;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    //좋아요 누른 레시피 조회
}
