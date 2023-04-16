package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {

}
