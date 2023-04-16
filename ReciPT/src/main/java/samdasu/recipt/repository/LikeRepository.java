package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
