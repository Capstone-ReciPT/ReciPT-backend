package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.User;
import samdasu.recipt.entity.UserAllergy;

import java.util.Optional;

public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long> {

    Optional<UserAllergy> findByUserAllergyAndUser(String userAllergy, User user);

}