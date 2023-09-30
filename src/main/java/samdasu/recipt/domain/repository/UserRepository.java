package samdasu.recipt.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByUsername(String username);
}